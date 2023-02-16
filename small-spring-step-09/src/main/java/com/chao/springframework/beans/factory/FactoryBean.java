package com.chao.springframework.beans.factory;

/**
 * 某些情况下，实例化 bean 过程比较复杂，如果按照传统的方式，则需要在 <bean></bean> 中提供大量的配置信息，
 * 配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。
 *
 * 用户可以通过实现 FactoryBean 接口定制实例化 bean 逻辑
 *
 * 主要用于复杂对象的创建，比如常见到的 Mapper，这个接口是经常发生变化的，而且其中的内容有时会很多。
 * 如果使用xml配置的方式创建过程会十分复杂
 * @param <T>
 */
public interface FactoryBean<T> {

    // 获取对象
    T getObject() throws Exception;

    // 对象类型
    Class<?> getObjectType();

    // 是否是单例对象，如果 isSingleton() 返回 true，则该实例会放到 Spring 容器中单实例缓存池中
    boolean isSingleton();

}
