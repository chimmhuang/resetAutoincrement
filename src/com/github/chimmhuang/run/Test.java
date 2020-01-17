package com.github.chimmhuang.run;

import com.github.chimmhuang.pojo.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Start here
 *
 * @author Chimm Huang
 */
public class Test {

    public static DatabaseMetaData dbMetaData;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        // 2.获得数据库连接
        /*
            使用DriverManager类中的静态方法
            static Connection getConnection(String url, String user, String password)
         */
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        dbMetaData = connection.getMetaData();

        getDataBaseInformations();

        getAllTableList("root");

        Map<String, Field> columnNameBySchemaNameAndTableName = getColumnNameBySchemaNameAndTableName("root", "demo");
        System.out.println(columnNameBySchemaNameAndTableName);

        getIndexInfo("root","demo");

        getAllPrimaryKeys("root","demo02");
    }


    /**
     * 获得数据库的一些相关信息
     */
    public static void getDataBaseInformations() throws Exception{
        try {
            System.out.println("数据库已知的用户: " + dbMetaData.getUserName());
            System.out.println("数据库的系统函数的逗号分隔列表: " + dbMetaData.getSystemFunctions());
            System.out.println("数据库的时间和日期函数的逗号分隔列表: " + dbMetaData.getTimeDateFunctions());
            System.out.println("数据库的字符串函数的逗号分隔列表: " + dbMetaData.getStringFunctions());
            System.out.println("数据库供应商用于 'schema' 的首选术语: " + dbMetaData.getSchemaTerm());
            System.out.println("数据库URL: " + dbMetaData.getURL());
            System.out.println("是否允许只读:" + dbMetaData.isReadOnly());
            System.out.println("数据库的产品名称:" + dbMetaData.getDatabaseProductName());
            System.out.println("数据库的版本:" + dbMetaData.getDatabaseProductVersion());
            System.out.println("驱动程序的名称:" + dbMetaData.getDriverName());
            System.out.println("驱动程序的版本:" + dbMetaData.getDriverVersion());

            System.out.println();
            System.out.println("数据库中使用的表类型");
            ResultSet rs = dbMetaData.getTableTypes();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得该用户下面的所有表
     */
    public static void getAllTableList(String schemaName) {
        try {
            // table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE",
            // "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
            String[] types = { "TABLE" };
            ResultSet rs = dbMetaData.getTables(null, schemaName, "%", types);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME"); // 表名
                String tableType = rs.getString("TABLE_TYPE"); // 表类型
                String remarks = rs.getString("REMARKS"); // 表备注
                System.out.println(tableName + "-" + tableType + "-" + remarks);

                String refGeneration = rs.getString("REF_GENERATION");
                System.out.println(refGeneration);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定用户名与表名获取其中列字段名
     *
     * @param schemaName
     * @param tableName
     * @return
     */
    public static Map<String, Field> getColumnNameBySchemaNameAndTableName(String schemaName, String tableName) {
        Map<String, Field> columns = null;
        try {
            columns = new HashMap<>();
            ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");// 列名
                String dataTypeName = rs.getString("TYPE_NAME");// java.sql.Types类型名称
                int columnSize = rs.getInt("COLUMN_SIZE");// 列大小
                Field field = new Field(columnName, dataTypeName, columnSize);
                columns.put(columnName, field);

                String isAutoincrement = rs.getString("IS_AUTOINCREMENT");
                System.out.println(isAutoincrement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }


    /**
     * 获得一个表的索引信息
     */
    public static void getIndexInfo(String schemaName, String tableName) {
        try {
            ResultSet rs = dbMetaData.getIndexInfo(null, schemaName, tableName, true, true);
            while (rs.next()) {
                boolean nonUnique = rs.getBoolean("NON_UNIQUE");// 非唯一索引(Can
                // index values
                // be
                // non-unique.
                // false when
                // TYPE is
                // tableIndexStatistic
                // )
                String indexQualifier = rs.getString("INDEX_QUALIFIER");// 索引目录（可能为空）
                String indexName = rs.getString("INDEX_NAME");// 索引的名称
                short type = rs.getShort("TYPE");// 索引类型
                short ordinalPosition = rs.getShort("ORDINAL_POSITION");// 在索引列顺序号
                String columnName = rs.getString("COLUMN_NAME");// 列名
                String ascOrDesc = rs.getString("ASC_OR_DESC");// 列排序顺序:升序还是降序
                int cardinality = rs.getInt("CARDINALITY"); // 基数
                System.out.println(nonUnique + "-" + indexQualifier + "-" + indexName + "-" + type + "-"
                        + ordinalPosition + "-" + columnName + "-" + ascOrDesc + "-" + cardinality);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得一个表的主键信息
     */
    public static void getAllPrimaryKeys(String schemaName, String tableName) {
        try {
            ResultSet rs = dbMetaData.getPrimaryKeys(null, schemaName, tableName);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");// 列名
                short keySeq = rs.getShort("KEY_SEQ");// 序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
                String pkName = rs.getString("PK_NAME"); // 主键名称
                System.out.println(columnName + "-" + keySeq + "-" + pkName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
