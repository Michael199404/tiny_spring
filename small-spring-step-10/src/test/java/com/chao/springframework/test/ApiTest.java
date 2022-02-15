package com.chao.springframework.test;

import com.chao.springframework.core.io.ClassPathXmlApplicationContext;
import com.chao.springframework.test.event.CustomEvent;
import org.junit.Test;

/**
 * https://www.cnblogs.com/ZeroMZ/p/11324173.html
 * 如果容器中有一个ApplicationListener bean，当ApplicationContext发布ApplicationEvent时，ApplicationListener bean将自动被触发
 *
 * spring的事件框架的两个重要成员（即Event、Listener）：
 * 1》ApplicationEvent：容器事件，必须由ApplicationContext发布
 * 2》ApplicationListener：监听器，可由容器中的任何监听器bean担任
 *
 * 事件机制中的3要素：事件源（ApplicationContext）、事件（Event）、事件监听器（Listener）
 *
 * Event事件 -----------> ApplicationContext事件源发布事件 -----------------> 触发Listener监听器
 * -------------------------> 监听器执行内部onApplicationEvent(ApplicationEvent e)方法，Event事件作为传入参数
 */
public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功j!"));
        applicationContext.registerShutdownHook();
    }
}
