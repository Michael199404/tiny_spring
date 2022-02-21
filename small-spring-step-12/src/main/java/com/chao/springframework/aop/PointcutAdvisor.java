package com.chao.springframework.aop;

import org.aspectj.lang.reflect.Pointcut;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
