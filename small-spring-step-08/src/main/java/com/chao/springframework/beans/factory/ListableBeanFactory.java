package com.chao.springframework.beans.factory;

import cn.hutool.core.bean.BeanException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回 Bean 实例
     * @param type
     * @param <T>
     * @return
     * @throws BeanException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 返回注册表中所有 Bean 名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
