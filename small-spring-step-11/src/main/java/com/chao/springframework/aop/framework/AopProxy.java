package com.chao.springframework.aop.framework;

/**
 * 定义一个接口用于获取代理类，因为具体实现有JDK方式也有Cglib方式
 */
public interface AopProxy {

    Object getProxy();
}
