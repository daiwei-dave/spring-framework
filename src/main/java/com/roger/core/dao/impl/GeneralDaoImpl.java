package com.roger.core.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roger.core.constant.OgnlParamConstant;
import com.roger.core.dao.GeneralDao;
import com.roger.core.db.GeneralOgnlParam;
import com.roger.core.db.SqlColumn;
import com.roger.core.db.SqlColumnFactory;
import com.roger.core.mapper.GeneralMapper;
import com.roger.core.utils.GeneralMapperReflectUtil;
import com.roger.core.utils.PersistentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneralDaoImpl implements GeneralDao {

    @Autowired
    protected GeneralMapper generalMapper;

    @Override
    public <T> T selectByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap) {
        String tableName = PersistentUtil.getTableName(clazz);
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);

        Map<String, Object> resultMap = generalMapper.selectByPrimaryKey(paramMap);
        if (CollectionUtils.isEmpty(resultMap)) {
            return null;
        }
        JSONObject resultJSONObject = JSON.parseObject(JSON.toJSONString(resultMap));
        return JSONObject.toJavaObject(resultJSONObject, clazz);
    }

    @Override
    public <T> List<T> selectAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam) {
        List<T> resultList = new ArrayList<>();
        List<String> columnNameList = GeneralMapperReflectUtil.getAllColumnNames(clazz);
        generalOgnlParam.setQueryCoulmn(columnNameList);

        List<Map<String, Object>> resultMapList = this.selectAdvancedByColumn(clazz, generalOgnlParam);
        for (Map<String, Object> resultMap : resultMapList) {
            T target = GeneralMapperReflectUtil.parseToJavaBean(resultMap, clazz);
            if (target != null) {
                resultList.add(target);
            }
        }

        return resultList;
    }

    private <T> List<Map<String, Object>> selectAdvancedByColumn(Class<T> clazz, GeneralOgnlParam generalOgnlParam) {

        String tableName = PersistentUtil.getTableName(clazz);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        paramMap.put(OgnlParamConstant.DML_COLUMN_NAME_LIST, generalOgnlParam.getQueryCoulmn());
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_EXP, generalOgnlParam.getConditionExp());
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_PARAM, generalOgnlParam.getConditionParam());
        paramMap.put(OgnlParamConstant.ORDER_BY_EXP, generalOgnlParam.getOrderExp());

        if (generalOgnlParam.isEnablePage()) {
            Map<String, Object> pageMap = new HashMap<>();
            int startRowNo = (generalOgnlParam.getPageNo() - 1) * generalOgnlParam.getPageSize();
            pageMap.put(OgnlParamConstant.START_ROW_NO, startRowNo);
            pageMap.put(OgnlParamConstant.PAGE_SIZE, generalOgnlParam.getPageSize());
            paramMap.put(OgnlParamConstant.PAGE, pageMap);
        }

        return generalMapper.selectAdvanced(paramMap);
    }

    @Override
    public <T> int insert(T target) {
        if (target == null) {
            return 0;
        }
        String tableName = PersistentUtil.getTableName(target.getClass());
        Map<String, Object> paramMap = new HashMap<>();
        List<SqlColumn> sqlColumnList = new ArrayList<>();
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        paramMap.put(OgnlParamConstant.SQL_COLUMN_LIST, sqlColumnList);
        constrSqlColumnList(sqlColumnList, target);

        return this.insert(paramMap);
    }

    @Override
    public int insert(Map<String, Object> paramMap) {
        return generalMapper.insert(paramMap);
    }

    @Override
    public <T> int batchInsert(List<T> targetList) {
        if (CollectionUtils.isEmpty(targetList)) {
            return 0;
        }
        T target = targetList.get(0);
        String tableName = PersistentUtil.getTableName(target.getClass());
        Map<String, Object> paramMap = new HashMap<>();
        //插入的表名
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        List<String> columnNameList = GeneralMapperReflectUtil.getAllColumnNames(target.getClass());
        //插入的字段
        paramMap.put(OgnlParamConstant.DML_COLUMN_NAME_LIST, columnNameList);
        List<List<SqlColumn>> dataList = new ArrayList<>();
        //插入的数据
        paramMap.put(OgnlParamConstant.BATCH_DATA_LIST, dataList);

        for (T t : targetList) {
            List<SqlColumn> sqlColumnList = new ArrayList<>();
            dataList.add(sqlColumnList);
            constrSqlColumnList(sqlColumnList, t);
        }

        return generalMapper.batchInsert(paramMap);
    }

    private <T> void constrSqlColumnList(List<SqlColumn> sqlColumnList, T target) {
        if (sqlColumnList == null) {
            sqlColumnList = new ArrayList<>();
        }
        List<Field> fieldList = PersistentUtil.getPersistentFields(target.getClass());
        for (Field field : fieldList) {
            SqlColumn sqlColumn = SqlColumnFactory.createSqlColumn(target, field);
            sqlColumnList.add(sqlColumn);
        }
    }

    @Override
    public <T> int update(T target, GeneralOgnlParam generalOgnlParam) {
        if (target == null) {
            return 0;
        }
        String tableName = PersistentUtil.getTableName(target.getClass());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_EXP, generalOgnlParam.getConditionExp());
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_PARAM, generalOgnlParam.getConditionParam());

        List<SqlColumn> sqlColumnList = new ArrayList<>();
        paramMap.put(OgnlParamConstant.SQL_COLUMN_LIST, sqlColumnList);
        constrSqlColumnList(sqlColumnList, target);

        return generalMapper.update(paramMap);
    }

    @Override
    public <T> int deleteByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap) {
        String tableName = PersistentUtil.getTableName(clazz);
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        return generalMapper.deleteByPrimaryKey(paramMap);
    }

    @Override
    public <T> int deleteAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam) {
        String tableName = PersistentUtil.getTableName(clazz);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put(OgnlParamConstant.TABLE_NAME, tableName);
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_EXP,generalOgnlParam.getConditionExp());
        paramMap.put(OgnlParamConstant.WHERE_CONDITION_PARAM,generalOgnlParam.getConditionParam());
        return generalMapper.deleteAdvanced(paramMap);
    }

}
