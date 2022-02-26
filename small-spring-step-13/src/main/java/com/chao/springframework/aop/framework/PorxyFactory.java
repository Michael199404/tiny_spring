package com.chao.springframework.aop.framework;

import com.chao.springframework.aop.AdvisedSupport;

public class PorxyFactory {

    private AdvisedSupport advisedSupport;

    public PorxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
