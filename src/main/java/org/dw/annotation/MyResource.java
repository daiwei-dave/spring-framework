package org.dw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模拟spring的@Resource注解
 * Created by daiwei on 2017/11/9.
 */
@Retention(RetentionPolicy.RUNTIME) // 指定注解保留的范围 (运行期)
@Target({ ElementType.FIELD, ElementType.METHOD }) // 允许注解标注的位置 (属性, 方法)
public @interface MyResource {
    public String name() default ""; // 提供name属性
}
