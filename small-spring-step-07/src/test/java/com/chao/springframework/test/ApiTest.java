package com.chao.springframework.test;

import com.chao.springframework.context.support.ClassPathXmlApplicationContext;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_xml() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果:" + result);
    }
}
