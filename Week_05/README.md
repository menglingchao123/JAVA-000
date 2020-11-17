学习笔记
### java动态代理
#### 代理模式中角色
1. 目标接口
    - 作用:定义对外暴露公共方法
2. 目标实现
    - 作用:业务逻辑具体实现
3. 代理类
    - 作用:对业务逻辑的增强
#### JDK动态代理
1. 原理:jvm在类加载时期,通过目标接口和实现类,动态生成二进制的class
2. 实现
    - 涉及目标类:java.lang.reflect.Proxy , java.lang.reflect.InvocationHandler
    - 创建过程
        - jvm会根据目标对象和目标接口在内存中生成和.class文件等同的字节码
        - 将二进制字节码转换为.class对象
        - 通过newInstance创建代理对象实例
3. InvocationHandler
```
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
```
4. 生成代理对象
```
 //生成目标对象的代理类实例
        //param:classloader,目标接口,invocationHandler
        OrderService service = (OrderService) Proxy.newProxyInstance(classLoader, interfaces, proxy);
```