package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.config.DynamicDatasourceHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 孟令超
 * @version 1.0
 * @desription 动态切换数据源配置
 * @date 2020/12/3
 * @since jdk1.8
 */
@Aspect
@Component
public class DynamicSelectorDatasourceAspect {

    @Pointcut("execution(* org.example.dao.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object beforeAdvice(JoinPoint joinPoint) {
        Object res = null;
        try {
            ProceedingJoinPoint point = (ProceedingJoinPoint) joinPoint;
            //获取方法签名
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            //通过方法签名获取方法实例
            Method method = methodSignature.getMethod();
            //获取代理类
            Class<?> clazz = joinPoint.getTarget().getClass();
            //设置数据源
            resolveDatasource(clazz, method);
            res = point.proceed();
            DynamicDatasourceHolder.removeDetermineCurrentLookupKey();
        } catch (Throwable e) {
            DynamicDatasourceHolder.removeDetermineCurrentLookupKey();
        }
        return res;
    }

    private void resolveDatasource(Class<?> clazz, Method m) {
        if (clazz != null && clazz.isAnnotationPresent(ReadOnly.class)) {
            //设置要切换的数据源
            DynamicDatasourceHolder.setDetermineCurrentLookupKey("secondary");
        }
        if (m != null && m.isAnnotationPresent(ReadOnly.class)) {
            //设置要切换的数据源
            DynamicDatasourceHolder.setDetermineCurrentLookupKey("secondary");
        }
    }

}
