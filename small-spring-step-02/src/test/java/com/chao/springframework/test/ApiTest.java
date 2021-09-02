package com.chao.springframework.test;

import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * 总结：
 * 1. 在Spring Bean容器的实现类中要重点关注类之间的职责和关系
 */
public class ApiTest {

    @Test
    public void testBeanFactory() {
        //1. 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //2. 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //3. 第一次获取bean
        UserService userService = (UserService)beanFactory.getBean("userService");
        userService.queryUserInfo();
        //4. 第二次获取bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }
}
