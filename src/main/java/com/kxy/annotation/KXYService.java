package com.kxy.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * service注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface KXYService {

    String value() default "";
}
