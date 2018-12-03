package com.roger.core.annotaion;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomTransactional {

    String value() default "";
}
