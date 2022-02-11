package com.chao.springframework.beans.factory;

public interface FactoryBean<T> {

    // 获取对象
    T getObject() throws Exception;

    // 对象类型
    Class<?> getObjectType();

    // 是否是单例对象
    boolean isSingleton();

}
