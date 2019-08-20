package com.kxy.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 控制层注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface KXYController {

    String value() default "";
}
