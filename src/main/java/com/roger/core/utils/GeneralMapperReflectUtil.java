package com.roger.core.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralMapperReflectUtil {

    /**
     * 获取持久化对象 在数据库中的列名
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<String> getAllColumnNames(Class<T> clazz) {
        List<String> cloumnNameList = new ArrayList<>();

        List<Field> fieldList = PersistentUtil.getPersistentFields(clazz);
        for (Field field : fieldList) {
            String columnName = PersistentUtil.getColumnName(field);
            cloumnNameList.add(columnName);
        }
        return cloumnNameList;
    }

    public static <T> T parseToJavaBean(Map<String, Object> map, Class<T> clazz) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            T t = clazz.newInstance();
            for (Map.Entry<String, Object> fieldEntry : map.entrySet()) {
                //列名 - 统一转换成小写
                // 便于后续处理,下划线转驼峰
                String columnName = fieldEntry.getKey();
                Field field = PersistentUtil.getFieldByColumnName(clazz, columnName);
                if (field != null) {
                    FieldReflectUtil.setFieldValue(t,field,fieldEntry.getValue());
                }
            }
            return t;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("通过反射实例化类,报错-" + e.getMessage());
        }
    }
}
