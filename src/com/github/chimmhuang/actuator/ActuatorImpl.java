package com.github.chimmhuang.actuator;

import com.github.chimmhuang.config.ConnectionConfig;
import com.github.chimmhuang.exception.ResetAutoincrementException;
import com.github.chimmhuang.pojo.Field;
import com.github.chimmhuang.pojo.Table;
import com.github.chimmhuang.validation.ConnectionValidator;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Chimm Huang
 */
public class ActuatorImpl implements Actuator {

    private static Logger logger = Logger.getLogger(ActuatorImpl.class);
    private static DatabaseMetaData dbMetaData;
    private static Connection connection;

    private static Map<String, Table> dataMap = new ConcurrentHashMap<>();

    private static String classDriver;
    private static String url;
    private static String userName;
    private static String password;

    static  {
        // validate connectionConfig
        ConnectionValidator.checkConnectionConfig();

        // init data
        ConnectionConfig connectionConfig = ConnectionConfig.getInstance();
        classDriver = connectionConfig.getClassDriver();
        url = connectionConfig.getUrl();
        userName = connectionConfig.getUsername();
        password = connectionConfig.getPassword();

        try {
            Class.forName(classDriver);
            connection = DriverManager.getConnection(url, userName, password);
            dbMetaData = connection.getMetaData();
        } catch (Exception e) {
            throw new ResetAutoincrementException("init error");
        }
    }

    @Override
    public void executeReset() {
        try {
            logger.info("-------------------------- start --------------------------");

            // init table
            initTable(userName);

            dataMap.entrySet().stream()
                    // filter out elements without primary keys and autoincrement
                    // 过滤掉没有主键和自增的元素
                    .filter(entry -> {
                        Table table = entry.getValue();
                        return table.getPrimaryKey() != null && table.getIncrementValue() != null;
                    })
                    // filter out elements that do not need to be reset
                    // 过滤掉不需要重置的元素
                    .filter(entry -> {
                        try {
                            Table table = entry.getValue();
                            String primaryKeyValue = getTheLastPrimaryKeyValue(table.getTableName(), table.getPrimaryKey());
                            long value = Long.parseLong(primaryKeyValue == null ? "1" : primaryKeyValue);
                            value++;

                            long currentIncrementValue = Long.parseLong(table.getIncrementValue());
                            currentIncrementValue++;

                            return currentIncrementValue != value;
                        } catch (SQLException e) {
                            logger.error("get the value of primary-key occurred exception: " + e.getMessage(), e);
                            return false;
                        }
                    })
                    // reset autoincrement value
                    .forEach(entry -> {
                        try {
                            Table table = entry.getValue();
                            String primaryKeyValue = getTheLastPrimaryKeyValue(table.getTableName(), table.getPrimaryKey());
                            long value = Long.parseLong(primaryKeyValue == null ? "0" : primaryKeyValue);
                            value++;

                            boolean resetFlag = resetAutoincrementValue(table.getTableName(), String.valueOf(value));

                            if (resetFlag) {
                                logger.info("            table: " + table.getTableName() + " , reset success");
                            } else {
                                logger.info("            table: " + table.getTableName() + " , reset false");
                            }
                        } catch (SQLException e) {
                            logger.error("reset autoincrement value occurred exception: " + e.getMessage(), e);
                        }

                    });

            logger.info("--------------------------- end ---------------------------");

        } catch (Exception e) {
            logger.error("execute reset autoincrement occurred exception: " + e.getMessage(), e);
        }
    }

    /**
     * 初始化数据
     */
    private void initTable(String schemaName) throws SQLException {
        // table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE",
        // "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
        String[] types = { "TABLE" };
        try (ResultSet rs = dbMetaData.getTables(null, schemaName, "%", types)) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                Table table = Table.builder()
                        .tableName(tableName)
                        .primaryKey(getPrimaryKey(userName, tableName))
                        .incrementValue(getAutoincrementValue(tableName))
                        .fieldList(getColumnFieldBySchemaNameAndTableName(userName, tableName))
                        .build();
                dataMap.put(tableName, table);
            }
        }
    }

    /**
     * 获得一个表的主键，若有多个主键，则获取序列号为1的主键
     */
    private String getPrimaryKey(String schemaName, String tableName) throws SQLException {

        String primaryKey = null;

        ResultSet rs = dbMetaData.getPrimaryKeys(null, schemaName, tableName);
        while (rs.next()) {
            short keySeq = rs.getShort("KEY_SEQ");
            if (keySeq == 1) {
                // a primary key with a keySeq of 1 is a primary key that can be incremented
                // 序号为1的主键才可以进行自动增长
                primaryKey = rs.getString("COLUMN_NAME");
                break;
            }
        }

        return primaryKey;
    }

    /**
     * 获取一个表的自动增长的值
     */
    private String getAutoincrementValue(String tableName) throws SQLException {

        String autoincrementValue = null;

        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE table_name = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, tableName);

        try (ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                autoincrementValue = resultSet.getString(1);
            }
        }

        return autoincrementValue;
    }

    /**
     * 根据指定用户名与表名获取其中列字段名
     */
    private List<Field> getColumnFieldBySchemaNameAndTableName(String schemaName, String tableName) throws SQLException {
        List<Field> fieldList = new ArrayList<>();

        ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            String dataTypeName = rs.getString("TYPE_NAME");// java.sql.Types类型名称
            int columnSize = rs.getInt("COLUMN_SIZE");
            boolean isAutoincrement = rs.getBoolean("IS_AUTOINCREMENT");
            Field field = new Field(columnName, dataTypeName, columnSize, isAutoincrement);
            fieldList.add(field);
        }

        return fieldList;
    }

    /**
     * 获取指定表的最后一个主键的值
     */
    private String getTheLastPrimaryKeyValue(String tableName, String primaryKey) throws SQLException {

        String primaryKeyValue = null;

        String sql = "SELECT " + primaryKey + " FROM " + tableName + " ORDER BY " + primaryKey + " DESC LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(sql);

        try (ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                primaryKeyValue = resultSet.getString(primaryKey);
            }
        }

        return primaryKeyValue;
    }

    /**
     * 重置自动增长的值
     */
    private boolean resetAutoincrementValue(String tableName,String resetValue) {
        try {

            String sql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,Long.parseLong(resetValue));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("reset autoincrement occurred exception: " + e.getMessage(), e);
        }

        return false;
    }
}
