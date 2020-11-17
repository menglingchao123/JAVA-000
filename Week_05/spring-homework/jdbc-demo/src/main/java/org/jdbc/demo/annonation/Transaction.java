package org.jdbc.demo.annonation;

import java.lang.annotation.*;

/**
 * 事务标记注解
 * @author lingchaomeng
 * @date 2020/11/17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transaction {
}
