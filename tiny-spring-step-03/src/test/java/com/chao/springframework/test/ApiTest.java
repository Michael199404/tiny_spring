package com.chao.springframework.test;

import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        //1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //2. 注入 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        //3. 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService", "超超");
        userService.queryUserInfo();
    }
}
