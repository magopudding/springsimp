package com.kxy.annotation;


import java.lang.annotation.*;

/**
 * service注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KXYService {

    String value() default "";
}
