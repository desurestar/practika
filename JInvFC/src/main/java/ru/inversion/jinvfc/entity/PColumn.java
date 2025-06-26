/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.inversion.jinvfc.entity;

/**
 *
 * @author perov
 */
public class PColumn {

    public PColumn(String columnName, String sqlType, String javaType) {
        this.columnName = columnName;
        this.sqlType = sqlType;
        this.javaType = javaType;
        
        primaryKey = false;
        isNotNull = false;
        isLov = false;
    }
    
    public PColumn(String columnName, String sqlType, String javaType, Boolean checkOn, Boolean primaryKey, 
                   String length, String regexp, String checkColumn, Boolean isNotNull,
                   ColumnPlace place, Boolean isLov, Integer scale) {
        this.columnName = columnName;
        this.sqlType = sqlType;
        this.javaType = javaType;
        this.checkOn = checkOn;
        this.primaryKey = primaryKey;
        this.length = length;
        this.regexp = regexp;
        this.checkColumn = checkColumn;
        this.isNotNull=isNotNull;
        this.isLov=isLov;
        this.place = place;
        this.scale = scale;
        }
 
    private ColumnPlace place;

    public ColumnPlace getPlace() {
        return place;
    }

    public void setPlace (ColumnPlace place) {
        this.place = place;
    }
   
    private Integer scale;

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    private String     columnName;
    private String     sqlType;
    private String     javaType;
    private Boolean    checkOn;
    private Boolean    isLov;
    private Boolean    primaryKey;

    public Boolean getIsLov() {
        return isLov;
    }

    public void setIsLov(Boolean isLov) {
        this.isLov = isLov;
    }
    private String    length;
    private String     regexp;
    private String     checkColumn; 
    private Boolean    isNotNull;

    public Boolean getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(Boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getCheckColumn() {
        return checkColumn;
    }

    public void setCheckColumn(String checkColumn) {
        this.checkColumn = checkColumn;
    }
    
    
//    public Boolean getCheckOn() {
//        return checkOn;
//        
//    }

//    public void setCheckOn(Boolean checkOn) {
//        this.checkOn = checkOn;        
//    }
    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
       
    }
    
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public String getJInvFieldClassName ()
    {
        return "java.lang.Long".equals (getJavaType ()) ? "JInvLongField" : "JInvTextField";
    }        
    
    public String getJInvColumnClassName ()
    {
        if ("java.time.LocalDate".equals (getJavaType ()) || "java.time.LocalDateTime".equals (getJavaType ()))
            return "JInvTableColumnDate";  
        
        if ("java.math.BigDecimal".equals (getJavaType ()))
            return new Integer (2).equals (getScale ()) ? "JInvTableColumnMoney" : "JInvTableColumnBigDecimal";
        
        return "JInvTableColumn";
    }        
    
    private String UpsPrefName;

    public String getUpsPrefName() {
        return UpsPrefName;
    }

    public void setUpsPrefName(String UpsPrefName) {
        this.UpsPrefName = UpsPrefName;
    }
 
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }    
}
