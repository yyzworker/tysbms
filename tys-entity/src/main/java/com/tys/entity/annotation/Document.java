package com.tys.entity.annotation;

import java.lang.annotation.*;

/**
 * @Author haoxu
 * @Date 2019/5/22 16:58
 **/
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {
    String indexName();

    String type() default "";

    boolean useServerConfiguration() default false;

    short shards() default 5;

    short replicas() default 1;

    String refreshInterval() default "1s";

    String indexStoreType() default "fs";

    boolean createIndex() default true;
}
