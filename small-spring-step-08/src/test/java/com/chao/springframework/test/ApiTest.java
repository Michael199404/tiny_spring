package com.chao.springframework.test;

import com.chao.springframework.core.io.ClassPathXmlApplicationContext;
import com.chao.springframework.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_xml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果:" + result);

        System.out.println("ApplicationContextAware:" + userService.getApplicationContext());
        System.out.println("BeanFactoryAware:" + userService.getBeanFactory());
    }

}
