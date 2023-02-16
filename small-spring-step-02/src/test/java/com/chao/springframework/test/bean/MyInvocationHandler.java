package com.chao.springframework.test.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    /**
     * 执行动态代理对象的所有方法时，都会被替换成执行如下的 invoke 方法
     * @param proxy 动态代理对象
     * @param method 正在执行的方法
     * @param args 执行代理对象方法时传入的实参
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("正在执行的方法" + method);
        if (args != null) {
            System.out.println("下面是执行该方法传入的实参:");
            for (Object val : args) {
                System.out.println(val);
            }
        } else {
            System.out.println("调用该方法无须实参");
        }
        return null;
    }
}
