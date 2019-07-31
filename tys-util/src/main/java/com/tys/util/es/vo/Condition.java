package com.tys.util.es.vo;

import com.tys.util.es.constant.AssociateType;
import com.tys.util.es.constant.QueryType;

import java.io.Serializable;

/**
 * @Author haoxu
 * @Date 2019/5/23 15:58
 **/
public class Condition implements Serializable {

    private String[] columns;

    private QueryType queryType;

    private AssociateType associateType;

    private Condition next;

    private Object value;

    public Condition() {
    }

    public Condition(String[] columns, QueryType queryType,  Object value) {
        this(columns, queryType, AssociateType.MUST, null, value);
    }

    public Condition(String[] columns, QueryType queryType, AssociateType associateType, Object value) {
        this(columns, queryType, associateType, null, value);
    }

    public Condition(String[] columns, QueryType queryType, AssociateType associateType, Condition next, Object value) {
        this.columns = columns;
        this.queryType = queryType;
        this.associateType = associateType;
        this.next = next;
        this.value = value;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public AssociateType getAssociateType() {
        return associateType;
    }

    public void setAssociateType(AssociateType associateType) {
        this.associateType = associateType;
    }

    public Condition getNext() {
        return next;
    }

    public void setNext(Condition next) {
        this.next = next;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
