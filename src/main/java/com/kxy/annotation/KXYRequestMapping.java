package com.kxy.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 请求映射url注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface KXYRequestMapping {

    String value() default "";

}
