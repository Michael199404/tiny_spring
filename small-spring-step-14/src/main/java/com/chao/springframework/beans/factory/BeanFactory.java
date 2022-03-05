package com.chao.springframework.beans.factory;

import com.chao.springframework.beans.BeansException;

/**
 * BeanFactory 主要功能就是获取一个 Bean 的实例
 * BeanFactory 提供 Bean 的获取方法
 * 之后，这个工厂抽象接口由抽象类 AbstractBeanFactory 实现
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
