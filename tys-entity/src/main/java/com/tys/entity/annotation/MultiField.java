package com.tys.entity.annotation;


import java.lang.annotation.*;

/**
 * @Author haoxu
 * @Date 2019/5/22 17:22
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MultiField {
    Field mainField();

    InnerField[] otherFields() default {};
}
