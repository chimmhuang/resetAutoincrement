package com.github.chimmhuang.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author huangshuai
 * @date 2020/01/17
 */
public class Test {

    public static DatabaseMetaData dbMetaData;

    public static void main(String[] args) throws Exception{
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



        //获取该用户下面的所有表
        String[] types = { "TABLE" };
        ResultSet rs = dbMetaData.getTables(null, "root", "%", types);
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME"); // 表名
            String tableType = rs.getString("TABLE_TYPE"); // 表类型
            String remarks = rs.getString("REMARKS"); // 表备注
            System.out.println(tableName + "-" + tableType + "-" + remarks);


            ResultSet resultSet = dbMetaData.getPrimaryKeys(null, "root", tableName);
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");// 列名
                short keySeq = resultSet.getShort("KEY_SEQ");// 序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
                String pkName = resultSet.getString("PK_NAME"); // 主键名称
                System.out.println(columnName + "-" + keySeq + "-" + pkName);


                //获取最后一个元素的值
                String sql = "SELECT id FROM " + tableName + " ORDER BY " + columnName + " DESC LIMIT 1";
                Statement statement = connection.createStatement();
                ResultSet resultSet1 = statement.executeQuery(sql);

                resultSet1.next();
                int id = resultSet1.getInt("id");
                System.out.println(resultSet1.getString("id"));

                id++;

                String sql2 = "alter table "+tableName+" auto_increment=" + id;

//                System.out.println(statement.execute(sql2));


                //获取自增的值
                String sql3 = "select auto_increment from " + tableName;
//                PreparedStatement preparedStatement = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
//                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//                generatedKeys.next();
//                System.out.println(generatedKeys.getInt(1));
                ResultSet resultSet2 = statement.executeQuery(sql3);
                resultSet2.next();
                System.out.println(resultSet2.getInt(1));


            }

        }
    }
}
