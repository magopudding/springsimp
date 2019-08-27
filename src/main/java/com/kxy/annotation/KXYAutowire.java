package com.kxy.annotation;


import java.lang.annotation.*;

/**
 * 依赖注入注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KXYAutowire {

    String value() default "";

}
