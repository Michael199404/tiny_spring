package com.chao.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.chao.springframework.beans.factory.config.BeanDefinition;
import com.chao.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidate = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidate.add(new BeanDefinition(clazz));
        }
        return candidate;
    }
}
