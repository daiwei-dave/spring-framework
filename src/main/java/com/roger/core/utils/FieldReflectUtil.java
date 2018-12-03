package com.roger.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class FieldReflectUtil {

    public static <T> void setFieldValue(T t, Field field, Object value) {
        ReflectionUtils.makeAccessible(field);
        try {
            if (value == null) {
                field.set(t, null);
                return;
            }

            if(field.getType().isEnum()){
                //Enum类型的字段在数据库中存储其ordinal
                Number number = TypeCastUtil.castToNumber(value);
                int ordinal = NumberUtil.toInt(number);
                EnumFieldReflectUtil.setFieldEnumValueByOrdinal(t,field,ordinal);
                return;
            }

            // Boolean类型字段处理
            if (field.getType().equals(Boolean.class)) {
                boolean b = TypeCastUtil.castToBoolean(value);
                field.set(t, b);
                return;
            }

            // Number类型字段处理
            if (Number.class.isAssignableFrom(field.getType())) {
                // oracle中Number类型返回的是BigDecimal
                NumberFieldReflectUtil.setFieldNumberValue(t, field, (Number) value);
                return;
            }
            // Date类型字段处理
            if (java.util.Date.class.isAssignableFrom(field.getType())) {
                DateFieldReflectUtil.setFieldDateValue(t, field, value);
                return;
            }

            field.set(t, value);

        } catch (IllegalAccessException e) {
            throw new RuntimeException("field can't set value to class " + t.getClass().getName());
        }
    }

    public static <T> Object getFieldValue(T target, Field field) {
        ReflectionUtils.makeAccessible(field);
        try {
            if (field.get(target) == null) {
                return null;
            }
            if (field.getType().isEnum()) {
                return EnumFieldReflectUtil.getEnumFieldOrdinal(target, field);
            }
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName() + ":field type is not Enum, can not convertToEnum");
        }
    }
}
