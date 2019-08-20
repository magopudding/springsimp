package com.kxy.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 依赖注入注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface KXYAutowire {

    String value() default "";

}
