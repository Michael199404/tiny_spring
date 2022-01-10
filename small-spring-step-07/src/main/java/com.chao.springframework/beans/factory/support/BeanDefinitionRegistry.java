package com.chao.springframework.beans.factory.support;

import com.chao.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的 BeanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
