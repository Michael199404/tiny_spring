package com.chao.springframework.test.dynaProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    /**
     * 需要被代理的对象
     */
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * 执行被代理对象的所有方法时，都将被替换成如下的 invoke 方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DogUtil du = new DogUtil();
        du.method1();
        Object result = method.invoke(target, args);
        du.method2();
        return result;
    }
}
