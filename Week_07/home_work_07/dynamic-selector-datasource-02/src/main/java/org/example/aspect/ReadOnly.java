package org.example.aspect;

import java.lang.annotation.*;

/**
 * @desription 动态切换数据源注解
 * @author 孟令超
 * @date 2020/12/3
 * @since jdk1.8
 * @version 1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReadOnly {

    /**
     * 数据源名称
     * @return
     */
    String value() default "secondary";

}
