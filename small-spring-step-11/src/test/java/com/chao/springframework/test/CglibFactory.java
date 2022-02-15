package com.chao.springframework.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibFactory implements MethodInterceptor {

    public SomeService myCglibCreator() {
        Enhancer enhancer = new Enhancer();
        // 将目标设置为父类, cglib动态代理的原理就是子类增强父类
        enhancer.setSuperclass(SomeService.class);
        enhancer.setCallback(this);
        // create()方法用于创建cglib动态代理对象
        return (SomeService) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(o, objects);
        if (result != null) {
            result = ((String)result).toUpperCase();
        }
        return result;
    }
}
