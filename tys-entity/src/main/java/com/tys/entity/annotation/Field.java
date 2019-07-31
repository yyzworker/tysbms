package com.tys.entity.annotation;

import java.lang.annotation.*;

/**
 * @Author haoxu
 * @Date 2019/5/22 16:58
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface Field {
    FieldType type() default FieldType.Auto;

    boolean index() default true;

    DateFormat format() default DateFormat.none;

    String pattern() default "";

    boolean store() default false;

    boolean fielddata() default false;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String normalizer() default "";

    String[] ignoreFields() default {};

    boolean includeInParent() default false;

    String[] copyTo() default {};
}
