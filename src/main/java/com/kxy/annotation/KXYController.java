package com.kxy.annotation;


import java.lang.annotation.*;

/**
 * 控制层注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KXYController {

    String value() default "";
}
