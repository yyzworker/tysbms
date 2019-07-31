package com.tys.util.es.vo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/5/23 15:45
 **/
public class QueryInfo implements Serializable {

    private Class clazz;

    private List<Condition> conditions;

    private Integer pageSize;

    private Integer pageNum;

    private String orderByName;

    private String orderByOrder;

    private String[] includes;

    private String[] excludes;

    public QueryInfo(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public String getOrderByOrder() {
        return orderByOrder;
    }

    public void setOrderByOrder(String orderByOrder) {
        this.orderByOrder = orderByOrder;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

}
