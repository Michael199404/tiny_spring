package com.chao.springframework.aop;

/**
 * 切入点接口
 */
public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
