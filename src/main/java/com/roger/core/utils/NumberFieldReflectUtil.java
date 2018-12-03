package com.roger.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class NumberFieldReflectUtil {
    /**
     * 设置Number类型字段值
     *
     * @param target
     *            the target object from which to get the field
     * @param field
     *            the field to set
     */
    public static void setFieldNumberValue(Object target, Field field, Object value) {

        if (!Number.class.isAssignableFrom(field.getType())) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName()
                    + " : field type is not Number, can not convertToNumber");
        }

        if (!field.isAccessible()) {
            ReflectionUtils.makeAccessible(field);
        }
        try {
            if (value == null) {
                field.set(target, null);
                return;
            }

            if (!(value instanceof Number)) {
                throw new RuntimeException(value + " : is not Number type value , can not convertToNumber to field "
                        + target.getClass().getName() + "." + field.getName());
            }

            Number number = (Number) value;

            if (field.getType().equals(Byte.class)) {
                field.set(target, NumberUtil.toByte(number));
                return;
            }
            if (field.getType().equals(Double.class)) {
                field.set(target, NumberUtil.toDouble(number));
                return;
            }
            if (field.getType().equals(Float.class)) {
                field.set(target, NumberUtil.toFloat(number));
                return;
            }
            if (field.getType().equals(Integer.class)) {
                field.set(target, NumberUtil.toInt(number));
                return;
            }
            if (field.getType().equals(Long.class)) {
                field.set(target, NumberUtil.toLong(number));
                return;
            }
            if (field.getType().equals(Short.class)) {
                field.set(target, NumberUtil.toShort(number));
                return;
            }
            if (field.getType().equals(java.math.BigDecimal.class)) {
                field.set(target, NumberUtil.toBigDecimal(number));
                return;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("field can't set value to class " + target.getClass().getName());
        }
    }
}
