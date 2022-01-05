package com.chao.springframework.test.common;

import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.PropertyValue;
import com.chao.springframework.beans.PropertyValues;
import com.chao.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
