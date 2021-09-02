package com.chao.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chao
 * @description BeanFactory可以存放bean以及获取bean
 * 在Bean工厂中，包括了Bean注册，这里注册的是 Bean 的定义信息。同时在这个类中还包括了获取 Bean 的操作
 * 目前的BeanFactory任然是非常简化的实现，但这种简化的实现内容也是整个Spring容器中关于Bean使用的最终
 * 提现结果，只不过实现过程只展示出基本的核心原理
 */
public class BeanFactory {

    private Map<String, BeanDefinition> beandefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name) {
        return beandefinitionMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beandefinitionMap.put(name, beanDefinition);
    }
}
