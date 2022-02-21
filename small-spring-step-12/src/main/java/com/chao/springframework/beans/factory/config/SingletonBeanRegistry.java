package com.chao.springframework.beans.factory.config;

/**
 * 单例注册表
 * 这个类比较简单，主要是定义了一个获取单例对象的接口
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);

}
