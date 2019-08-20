package com.kxy.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface RequestParam {


    String value() default "";
}
