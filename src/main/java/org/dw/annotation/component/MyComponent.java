package org.dw.annotation.component;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 模拟spring的@Component注解
 * @author daiwei
 * @date 2017/12/3.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {
    String value() default "";
}
