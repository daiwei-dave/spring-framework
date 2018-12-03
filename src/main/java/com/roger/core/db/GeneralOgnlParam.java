package com.roger.core.db;

import java.util.List;
import java.util.Map;

public class GeneralOgnlParam {
    /**
     * 查询列
     */
    private List<String> queryCoulmn;

    /**
     * 查询条件 where 表达式
     * 不要写where
     * 传入的参数格式为:#{conditionParam.paramName}
     */
    private String conditionExp;

    /**
     * 查询条件 where 表达式中的参数集
     * key:paramName
     */
    private Map<String, Object> conditionParam;

    private Integer pageSize;
    private Integer pageNo;
    private boolean enablePage;

    /**
     * order by 表达式
     * 格式:columnName asc|desc
     * 多个排序字段用逗号分隔
     */
    private String orderExp;

    public List<String> getQueryCoulmn() {
        return queryCoulmn;
    }

    public void setQueryCoulmn(List<String> queryCoulmn) {
        this.queryCoulmn = queryCoulmn;
    }

    public String getConditionExp() {
        return conditionExp;
    }

    public void setConditionExp(String conditionExp) {
        this.conditionExp = conditionExp;
    }

    public Map<String, Object> getConditionParam() {
        return conditionParam;
    }

    public void setConditionParam(Map<String, Object> conditionParam) {
        this.conditionParam = conditionParam;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrderExp() {
        return orderExp;
    }

    public void setOrderExp(String orderExp) {
        this.orderExp = orderExp;
    }

    public boolean isEnablePage() {
        return enablePage;
    }

    public void setEnablePage(boolean enablePage) {
        this.enablePage = enablePage;
    }
}
