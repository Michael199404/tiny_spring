package com.chao.springframework.aop;

/**
 * 定义类匹配，用于切点找到给定的接口和目标类
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);
}
