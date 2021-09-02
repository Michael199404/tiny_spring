package com.chao.springframework.beans.factory.support;

import com.chao.springframework.beans.factory.config.BeanDefinition;

/**
 * 在 AbstractAutowiredCapableBeanFactory 中实现了 Bean 的实例化操作 newInstance
 * 有构造函数入参的对象怎么处理？
 *
 * 在处理完 Bean 对象的实例化之后，直接调用 addSingleton 方法存放单例对象到缓存中去
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        addSingleton(beanName, bean);
        return bean;
    }
}
