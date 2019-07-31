package com.tys.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author haoxu
 * @Date 2019/5/22 17:22
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface InnerField {
    String suffix();

    FieldType type();

    boolean index() default true;

    DateFormat format() default DateFormat.none;

    String pattern() default "";

    boolean store() default false;

    boolean fielddata() default false;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String normalizer() default "";
}