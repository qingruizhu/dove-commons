package com.dove.common.base.annotation;


import java.lang.annotation.*;


/**
 * 自定义统一处理Restful风格响应，进行CommonResult包装
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonResultAnnon {
    boolean DEFAULT_ALLOW_RESULT = true;

    boolean use() default DEFAULT_ALLOW_RESULT;
}
