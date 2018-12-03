package com.roger.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class EnumFieldReflectUtil {

    /**
     * 获取枚举类型的字段值-ordinal
     *
     * @param target the target object from which to get the field
     * @param field  the field to get
     * @return enum.ordinal
     */
    public static int getEnumFieldOrdinal(Object target, Field field) {

        if (!field.getType().isEnum()) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName() + ":field type is not Enum, can not convertToEnum");
        }

        if (!field.isAccessible()) {
            ReflectionUtils.makeAccessible(field);
        }

        try {
            return ((Enum) field.get(target)).ordinal();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName() + ":field access Error.");
        }
    }

    /**
     * 设置枚举类型的字段值
     *
     * @param target  the target object from which to get the field
     * @param field   the field to set
     * @param ordinal enum.ordinal
     */
    @SuppressWarnings("rawtypes")
    public static void setFieldEnumValueByOrdinal(Object target, Field field, Integer ordinal) {
        if (!field.getType().isEnum()) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName()
                    + " : field type is not Enum, can not convertToEnum");
        }
        try {
            if (!field.isAccessible()) {
                ReflectionUtils.makeAccessible(field);
            }

            if (ordinal == null) {
                field.set(target, null);
                return;
            }

            Enum[] enumObjs = (Enum[]) (field.getType()).getEnumConstants();
            for (Enum enumObj : enumObjs) {
                if (enumObj.ordinal() == ordinal) {
                    field.set(target, enumObj);
                }
            }
        }catch (IllegalAccessException e){
            throw new RuntimeException("field can't set value to class " + target.getClass().getName());
        }

    }
}
