package com.roger.core.db;

import com.roger.core.utils.FieldReflectUtil;
import com.roger.core.utils.PersistentUtil;

import java.lang.reflect.Field;
import java.sql.Types;

public class SqlColumnFactory {

    public static SqlColumn createSqlColumn(String columnName,Object columnValue,String jdbcType){
        SqlColumn sqlColumn = new SqlColumn();
        sqlColumn.setColumnName(columnName);
        sqlColumn.setColumnValue(columnValue);
        sqlColumn.setJdbcType(jdbcType);
        return sqlColumn;
    }

    public static <T> SqlColumn createSqlColumn(T target, Field field){
        String columnName = PersistentUtil.getColumnName(field);
        Object columnValue = FieldReflectUtil.getFieldValue(target,field);
        Class<?> jdbcType = field.getType();
        return createSqlColumn(columnName,columnValue,matchJdbcType(jdbcType));
    }

    public static String matchJdbcType(Class<?> fieldType) {
        if(fieldType.isEnum()){
            //枚举类型存ordinal
            return "Integer";
        }
        if (String.class.equals(fieldType)) {
            //CLOB未完成
            return "VARCHAR";
        }
        if (Integer.class.equals(fieldType) || Integer.TYPE.equals(fieldType)) {
            return "INTEGER";
        }
        if (Double.class.equals(fieldType) || Double.TYPE.equals(fieldType)) {
            return "DOUBLE";
        }
        if (Float.class.equals(fieldType) || Float.TYPE.equals(fieldType)) {
            return "FLOAT";
        }
        if (java.util.Date.class.isAssignableFrom(fieldType)) {
            return "TIMESTAMP";
        }
        //CLOB BLOB未完成
        return null;
    }
    /**
     * 将jdbcType的int值转换成对应的SQL的列类型
     * @param jdbcType
     * @return
     */
    public static String matchJdbcType(int jdbcType) {
        if (Types.INTEGER==jdbcType) {
            // 枚举类型存ordinal
            return "Integer";
        }
        if (Types.VARCHAR==jdbcType) {
            // CLOB未完成
            return "VARCHAR";
        }
        if (Types.INTEGER==jdbcType) {
            return "INTEGER";
        }
        if (Types.DOUBLE==jdbcType) {
            return "DOUBLE";
        }
        if (Types.FLOAT==jdbcType) {
            return "FLOAT";
        }
        if (Types.TIMESTAMP==jdbcType) {
            return "TIMESTAMP";
        }
        if (Types.CHAR==jdbcType) {
            return "CHAR";
        }
        if (Types.NUMERIC==jdbcType) {
            return "NUMERIC";
        }

        // CLOB BLOB未完成
        return null;
    }
}
