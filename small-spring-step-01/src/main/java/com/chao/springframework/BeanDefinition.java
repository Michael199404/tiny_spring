package com.chao.springframework;

/**
 * @author chao
 * @description 定义一个Object类型用于存放对象
 * 目前的 Bean 定义中，只有一个 Object 用于存放 Bean 对象。如果感兴趣可以参考 Spring 源码中这个类的信息，名称都是一样的
 * 不过在后面陆续的实现中会逐步完善 BeanDefinition 相关属性的填充，例如：SCOPE_SINGLETON、SCOPE_PROTOTYPE、
 * ROLE_APPLICATION、ROLE_SUPPORT、ROLE_INFRASTRUCTURE 以及 Bean Class 信息
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }
}
