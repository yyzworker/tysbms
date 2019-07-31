package com.tys.entity.annotation;

/**
 * @Author haoxu
 * @Date 2019/5/22 17:23
 **/
public enum FieldType {
    Text,
    Integer,
    Long,
    Date,
    Float,
    Double,
    Boolean,
    Object,
    Auto,
    Nested,
    Ip,
    Attachment,
    Keyword;

    private FieldType() {
    }
}