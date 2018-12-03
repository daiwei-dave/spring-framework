package com.roger.core.db;

public class SqlPrimaryKeyColumn {

    private String sqlPrimaryKeyColumnName;
    private Object sqlPrimaryKeyColumnValue;
    private String jdbcType;

    public String getSqlPrimaryKeyColumnName() {
        return sqlPrimaryKeyColumnName;
    }

    public void setSqlPrimaryKeyColumnName(String sqlPrimaryKeyColumnName) {
        this.sqlPrimaryKeyColumnName = sqlPrimaryKeyColumnName;
    }

    public Object getSqlPrimaryKeyColumnValue() {
        return sqlPrimaryKeyColumnValue;
    }

    public void setSqlPrimaryKeyColumnValue(Object sqlPrimaryKeyColumnValue) {
        this.sqlPrimaryKeyColumnValue = sqlPrimaryKeyColumnValue;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
