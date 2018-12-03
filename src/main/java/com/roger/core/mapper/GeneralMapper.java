package com.roger.core.mapper;

import java.util.List;
import java.util.Map;

public interface GeneralMapper {

    Map<String,Object> selectByPrimaryKey(Map<String, Object> param);

    /**
     * 高级查询：参数可以动态设置
     * @param param
     * @return
     */
    List<Map<String,Object>> selectAdvanced(Map<String, Object> param);

    int insert(Map<String, Object> param);

    int batchInsert(Map<String, Object> param);

    int update(Map<String, Object> param);

    int deleteByPrimaryKey(Map<String, Object> param);

    /**
     * 1.高级删除
     * 2.此操作需要谨慎，如果不传递删除条件，则会把整张表的数据情况
     */
    int deleteAdvanced(Map<String, Object> param);
}
