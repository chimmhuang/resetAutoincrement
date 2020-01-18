package com.github.chimmhuang.pojo;

/**
 * @author Chimm Huang
 */
public class Field {
    private String fieldName;
    private String fieldType;
    private Integer fieldLength;
    private Boolean isAutoincrement;

    public Field() {
    }

    public Field(String fieldName, String fieldType, Integer fieldLength, Boolean isAutoincrement) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldLength = fieldLength;
        this.isAutoincrement = isAutoincrement;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }

    public Boolean getAutoincrement() {
        return isAutoincrement;
    }

    public void setAutoincrement(Boolean autoincrement) {
        isAutoincrement = autoincrement;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldLength=" + fieldLength +
                ", isAutoincrement=" + isAutoincrement +
                '}';
    }
}
