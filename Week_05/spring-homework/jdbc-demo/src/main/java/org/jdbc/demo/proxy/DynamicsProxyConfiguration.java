package org.jdbc.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 通过java的动态代理实现对目标对象增强操作
 * @author lingchaomeng
 * @date 2020/11/17
 */
public class DynamicsProxyConfiguration {

    public static void main(String[] args) {

        OrderServiceImpl orderService = new OrderServiceImpl();
        //获取classloader
        ClassLoader classLoader = orderService.getClass().getClassLoader();
        //获取实现接口
        Class<?>[] interfaces = orderService.getClass().getInterfaces();
        //创建执行目标对象方法
        ObjectProxy<OrderService> proxy = new ObjectProxy<OrderService>(orderService);
        //生成目标对象的代理类实例
        //param:classloader,目标接口,invocationHandler
        OrderService service = (OrderService) Proxy.newProxyInstance(classLoader, interfaces, proxy);
        //执行目标对象方法
        List<String> strings = service.get();
    }


    public static class ObjectProxy<T> implements InvocationHandler {

        //InvocationHandler是通过代理类对象执行被代理类对象的方法
        private T target;

        public ObjectProxy(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("前置执行");
            Object result = method.invoke(target, args);
            System.out.println(result);
            System.out.println("后置执行");
            return result;
        }
    }
}
