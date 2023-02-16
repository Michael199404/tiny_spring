package com.chao.springframework.test.bean;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        MyInvocationHandler handler = new MyInvocationHandler();
        // 使用指定的 InvocationHandler 来生成一个动态代理对象
        Person p = (Person)Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, handler);
        // 不管执行代理对象的 walk() 方法，还是代理对象的 sayHello() 方法，实际上都是执行 InvocationHandler 对象的 invoke() 方法
        p.walk();
        p.sayHello("老孙");
    }
}
