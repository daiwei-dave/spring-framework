package com.roger.core.utils;

import com.roger.core.constant.SeparatorConstant;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistentUtil {
    /**
     * 驼峰_下划线转换,默认开启</br>
     * 对@Table注解无效,支持ClassName_TableName</br>
     * 对@Column注解无效,支持fieldName_columnName</br>
     */
    private static final boolean CAMEL_TO_UNDERLINE = true;

    public static <T> String getTableName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            String tableName = table.name();
            if (!"".equals(tableName.trim())) {
                return tableName.trim();
            }
        }
        String clazzName = clazz.getSimpleName();
        if (!CAMEL_TO_UNDERLINE) {
            return clazzName;
        }
        return StringUtil.camelToUnderline(clazzName);
    }

    /**
     * 获取表的列名
     * 默认下划线风格
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            String columnName = column.name();
            if (!"".equals(columnName.trim())) {
                return columnName.trim();
            }
        }

        if (!CAMEL_TO_UNDERLINE) {
            return field.getName();
        }
        return StringUtil.camelToUnderline(field.getName());
    }


    public static <T> Field getFieldByColumnName(Class<T> clazz, String columnName) {
        Map<String, Field> columnNameFieldMapping = getColumnNameFieldMapping(clazz);
        Field field = columnNameFieldMapping.get(clazz.getName() + SeparatorConstant.DOT + columnName);
        if (field == null) {
            throw new RuntimeException("no search matched field to columnName :" + columnName + " from " + clazz.getName());
        }
        return field;
    }

    /**
     * 获取列名和Field的映射关系
     *
     * @param clazz
     * @return
     */
    public static Map<String, Field> getColumnNameFieldMapping(Class<?> clazz) {
        List<Field> fieldList = getPersistentFields(clazz);
        if (CollectionUtils.isEmpty(fieldList)) {
            return null;
        }
        Map<String, Field> mapping = new HashMap<String, Field>();
        String className = clazz.getName();
        for (Field field : fieldList) {
            mapping.put(className + SeparatorConstant.DOT + getColumnName(field), field);
        }
        return mapping;
    }


    /**
     * 获取持久化对象的持久化字段
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<Field> getPersistentFields(Class<T> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> searchType = clazz;
        while (!(Object.class.equals(searchType)) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if (isPersistentField(field)) {
                    fieldList.add(field);
                }
            }
            searchType = searchType.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 是否为持久化字段
     * javax.persistence.Transient注解为非持久化字段
     *
     * @param field
     * @return
     */
    public static boolean isPersistentField(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }
}
