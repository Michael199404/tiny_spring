package com.chao.springframework.beans.factory;

/**
 * 主要用于复杂对象的创建，比如常见到的 Mapper，这个接口是经常发生变化的，而且其中的内容有时会很多。
 * 如果使用xml配置的方式创建过程会十分复杂
 * @param <T>
 */
public interface FactoryBean<T> {

    // 获取对象
    T getObject() throws Exception;

    // 对象类型
    Class<?> getObjectType();

    // 是否是单例对象
    boolean isSingleton();

}
