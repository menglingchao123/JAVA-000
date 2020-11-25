package org.jdbc.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jdbc.demo.annonation.Transaction;
import org.jdbc.demo.config.JdbcConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;

/**
 * 事务增强的aop实现
 *
 * @author lingchaomeng
 * @date 2020/11/17
 */
@Aspect
@Component
public class TransactionProxyAspect {

    @Resource
    private JdbcConfiguration jdbcConfiguration;

    @Around("@annotation(transaction)")
    public Object transactionHandle(JoinPoint joinPoint, Transaction transaction) {
        Object result = null;
        try (Connection connection = jdbcConfiguration.getConnection()) {
            ProceedingJoinPoint point = (ProceedingJoinPoint) joinPoint;
            //开启事务
            connection.setAutoCommit(false);
            //调用方法
            result = point.proceed();
            //提交事务
            connection.commit();
        } catch (Throwable t) {
            jdbcConfiguration.rollback(jdbcConfiguration.getConnection());
            t.printStackTrace();
        }
        return result;
    }
}
