package com.chao.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanException;
import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.BeanFactory;
import com.chao.springframework.beans.factory.config.BeanDefinition;

/**
 * AbstractBeanFactory 继承实现了 SingletonBeanRegistry 的 DefaultSingletonBeanRegistry，
 * 这样，AbstractBeanFactory 抽象类就具备了单例 Bean 的注册功能
 *
 * 接下来很重要的一点是关于接口 BeanFactory 的实现，在方法 getBean 的实现过程中可以看到，主要是对单例 Bean 对象
 * 的获取以及在获取不到时需要拿到 Bean 的定义做 Bean 实例化操作
 *
 * getBean 并没有自身去实现这些方法，而是只定义了调用过程以及提供了抽象方法，由实现此抽象类的其他类做相应实现
 */

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return (T) getBean(name);
    }

    // 泛型方法，是在调用方法的时候指明泛型的具体类型
    protected <T> T doGetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}
