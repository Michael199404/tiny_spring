package com.chao.springframework.aop;

import com.chao.springframework.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;

public class AdvisedSupport {

    private TargetSource targetsource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    public TargetSource getTargetsource() {
        return targetsource;
    }

    public void setTargetsource(TargetSource targetsource) {
        this.targetsource = targetsource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
