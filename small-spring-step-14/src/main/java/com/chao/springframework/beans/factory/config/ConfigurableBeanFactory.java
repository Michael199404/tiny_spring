package com.chao.springframework.beans.factory.config;

import com.chao.springframework.beans.factory.HierarchicalBeanFactory;
import com.chao.springframework.util.StringValueResolver;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    void addEmbeddedValueReslver(StringValueResolver resolver);

    String resolveEmbeddedValue(String value);
}
