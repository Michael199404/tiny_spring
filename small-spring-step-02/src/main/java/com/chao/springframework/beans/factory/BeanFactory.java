package com.chao.springframework.beans.factory;

/**
 * BeanFactory 提供 Bean 的获取方法
 * 之后，这个工厂抽象接口由抽象类 AbstractBeanFactory 实现
 */
public interface BeanFactory {

    Object getBean(String name);
}
