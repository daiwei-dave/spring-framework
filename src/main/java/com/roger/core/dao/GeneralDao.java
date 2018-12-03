package com.roger.core.dao;

import com.roger.core.db.GeneralOgnlParam;

import java.util.List;
import java.util.Map;

public interface GeneralDao {

    <T> T selectByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap);

    <T> List<T> selectAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam);

    <T> int insert(T target);

    <T> int batchInsert(List<T> targetList);

    int insert(Map<String, Object> paramMap);

    <T> int update(T target, GeneralOgnlParam generalOgnlParam);

    <T> int deleteByPrimaryKey(Class<T> clazz, Map<String, Object> paramMap);

    /**
     * 1.高级删除
     * 2.此操作需要谨慎，如果不传递删除条件，则会把整张表的数据情况
     */
    <T> int deleteAdvanced(Class<T> clazz, GeneralOgnlParam generalOgnlParam);

}
