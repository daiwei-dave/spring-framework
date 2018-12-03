package com.roger.core.utils;

import java.util.Date;

public class DateUtil {

    /**
     *sql.date
     */
    public static java.sql.Date toSQLDate(Date date) {
        return date instanceof java.sql.Date ? (java.sql.Date) date : toSQLDate(date.getTime());
    }

    public static java.sql.Date toSQLDate(long time) {
        return new java.sql.Date(time);
    }

    public static java.sql.Date getSQLDate() {
        return toSQLDate(System.currentTimeMillis());
    }

    /**
     * sql.Timestamp
     */
    public static java.sql.Timestamp toTimestamp(Date date) {
        return date instanceof java.sql.Timestamp ? (java.sql.Timestamp) date : toTimestamp(date.getTime());
    }

    public static java.sql.Timestamp toTimestamp(long time) {
        return new java.sql.Timestamp(time);
    }

    public static java.sql.Timestamp getTimestamp() {
        return toTimestamp(System.currentTimeMillis());
    }

}
