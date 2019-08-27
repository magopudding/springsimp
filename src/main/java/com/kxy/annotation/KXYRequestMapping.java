package com.kxy.annotation;


import java.lang.annotation.*;

/**
 * 请求映射url注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KXYRequestMapping {

    String value() default "";

}
