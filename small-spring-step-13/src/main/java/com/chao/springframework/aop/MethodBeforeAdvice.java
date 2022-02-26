package com.chao.springframework.aop;

import java.lang.reflect.Method;

/**
 * Advice invoked before a method is invoked. Such advices cannot
 * prevent the method call proceeding，unless they throw a Throwable.
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * Callback before a given method is invoked.
     *
     * @param method method being invoked
     * @param args arguments to the method
     * @param object
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object object) throws Throwable;

}
