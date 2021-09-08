package com.chao.springframework.beans.factory.support;

import com.chao.springframework.beans.BeansException;
import com.chao.springframework.beans.factory.config.BeanDefinition;

import java.beans.Beans;
import java.lang.reflect.Constructor;

/**
 * 在 AbstractAutowiredCapableBeanFactory 中实现了 Bean 的实例化操作 newInstance
 * 有构造函数入参的对象怎么处理？
 *
 * 在处理完 Bean 对象的实例化之后，直接调用 addSingleton 方法存放单例对象到缓存中去
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
//            bean = beanDefinition.getBeanClass().newInstance();
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        // 需要循环对比出构造函数集合与入参信息 args 的匹配情况，实际 Spring 源码还需要比较入参类型，否则相同数量不同入参类型的情况，就会抛异常了
        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
