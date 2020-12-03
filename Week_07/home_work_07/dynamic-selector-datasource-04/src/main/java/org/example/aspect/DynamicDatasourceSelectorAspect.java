package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.config.DynamicDatasourceHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

@Aspect
@Component
public class DynamicDatasourceSelectorAspect {

    @Value("${load.balance.list}")
    private List<String> names;

    @Pointcut("execution(* org.example.dao..*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object intercept(JoinPoint point) {
        Object res = null;
        try {
            ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) point;
            //获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取代理类
            Class<?> clazz = joinPoint.getTarget().getClass();
            //获取拦截方法
            Method method = signature.getMethod();
            //设置数据源
            resovleDatasource(method, clazz);
            res = ((ProceedingJoinPoint) point).proceed();
            DynamicDatasourceHolder.removeDetermineCurrentLookupKey();
        } catch (Throwable e) {
            DynamicDatasourceHolder.removeDetermineCurrentLookupKey();
        }
        return res;
    }

    private void resovleDatasource(Method m, Class<?> clazz) {
        if (clazz != null && clazz.isAnnotationPresent(ReadOnly.class)) {
            DynamicDatasourceHolder.setDetermineCurrentLookupKey(loadBalance());
        }
        if (m != null && m.isAnnotationPresent(ReadOnly.class)) {
            DynamicDatasourceHolder.setDetermineCurrentLookupKey(loadBalance());
        }
    }

    private String loadBalance(){
        Random random = new Random();
        int index = random.nextInt(names.size());
        return names.get(index);
    }
}
