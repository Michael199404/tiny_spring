package com.chao.springframework.test;

import com.chao.springframework.beans.PropertyValue;
import com.chao.springframework.beans.PropertyValues;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.beans.factory.config.BeanReference;
import com.chao.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.chao.springframework.test.bean.UserDao;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性【uId、userDao】
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入 Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }


}
