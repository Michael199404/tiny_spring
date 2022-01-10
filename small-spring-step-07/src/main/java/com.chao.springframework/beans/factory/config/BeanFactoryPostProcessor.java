package com.chao.springframework.beans.factory.config;

import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许自定义修改BeanDefinition 属性信息
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完毕之后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
