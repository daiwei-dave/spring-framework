package com.roger.core.utils;

public class TypeCastUtil {
    public static Number castToNumber(Object value) {
        if (value == null) {
            return null;
        }
        if (TypeIdentifyUtil.isNumberType(value)) {
            return (Number) value;
        }
        if (value instanceof Boolean || value.getClass().equals(Boolean.TYPE)) {
            return Boolean.valueOf((Boolean) value) ? 1 : 0;
        }
        throw new RuntimeException(value + " : value is not Number type or Boolean type, can not castToNumber");
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (TypeIdentifyUtil.isBooleanType(value)) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() == 1 ? true : false;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }

        throw new RuntimeException(value + " : value is not Number type or Boolean type, can not castToBoolean");
    }
}
