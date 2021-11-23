package com.chao.springframework.beans.factory.support;

import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * DefaultListableBeanFactory 在 Spring 源码中也是一个非常核心的类
 * DefaultListableBeanFactory 继承了 AbstractAutowiredCapableBeanFactory 类，也就具备了接口
 * 现在注册Bean定义与获取Bean定义就可以同时使用了
 *
 */
public class DefaultListableBeanFactory extends AbstractAutowiredCapableBeanFactory implements BeanDefinitionRegistry{

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named " + beanName + "is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
//        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
//        if (beanDefinition != null) {
//            return true;
//        }
//        return false;
        return beanDefinitionMap.containsKey(beanName);
    }
}
