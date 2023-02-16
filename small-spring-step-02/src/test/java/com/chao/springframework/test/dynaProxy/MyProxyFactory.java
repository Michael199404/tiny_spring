package com.chao.springframework.test.dynaProxy;

import java.lang.reflect.Proxy;

/**
 * 为指定的 target 生成动态代理实例
 * 下面的动态代理工厂类提供了一个 getProxy 方法，该方法为 target 对象生成一个动态代理对象
 *
 */
public class MyProxyFactory {
    /**
     *  为指定的 target 生成动态代理对象
     */
    public static Object getProxy(Object target) {
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(target);
        // 创建并返回一个动态代理
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }
}
