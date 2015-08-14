package com.hdu.newlife.bean;

import java.io.Serializable;

/**
 * 类名:		ExcelColumnBean
 * 描述:		解析Excel列
 * @author newlife
 *
 */
public class ExcelColumnBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer           columnIndex;

    private String            columnName;

    private String            value;

    public ExcelColumnBean(){
        
    }
    
    public ExcelColumnBean(Integer columnIndex, String columnName, String value) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        this.value = value;
    }

    /**
     * @return 返回变量columnIndex的值
     */
    public Integer getColumnIndex() {
        return columnIndex;
    }

    /**
     * @param columnIndex 设置columnIndex的值
     */
    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * @return 返回变量columnName的值
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName 设置columnName的值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return 返回变量value的值
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 设置value的值
     */
    public void setValue(String value) {
        this.value = value;
    }

}
