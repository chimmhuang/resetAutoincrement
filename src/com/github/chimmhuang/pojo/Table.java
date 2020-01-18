package com.github.chimmhuang.pojo;

import com.github.chimmhuang.config.ConnectionConfig;
import com.github.chimmhuang.config.ConnectionConfig.Builder;

import java.util.List;

/**
 * table info
 *
 * @author Chimmm Huang
 */
public class Table {
    private String tableName;
    private String primaryKey;
    private String incrementValue;
    private List<Field> fieldList;

    public Table() {
    }

    public Table(String tableName, String primaryKey, String incrementValue, List<Field> fieldList) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.incrementValue = incrementValue;
        this.fieldList = fieldList;
    }

    public static Table.Builder builder() {
        return new Table.Builder();
    }

    public static class Builder {

        private Table table = new Table();

        public Table.Builder tableName(String tableName) {
            table.setTableName(tableName);
            return this;
        }

        public Table.Builder primaryKey(String primaryKey) {
            table.setPrimaryKey(primaryKey);
            return this;
        }

        public Table.Builder incrementValue(String incrementValue) {
            table.setIncrementValue(incrementValue);
            return this;
        }

        public Table.Builder fieldList(List<Field> fieldList) {
            table.setFieldList(fieldList);
            return this;
        }

        public Table build() {
            return table;
        }

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getIncrementValue() {
        return incrementValue;
    }

    public void setIncrementValue(String incrementValue) {
        this.incrementValue = incrementValue;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", incrementValue='" + incrementValue + '\'' +
                ", fieldList=" + fieldList +
                '}';
    }
}
