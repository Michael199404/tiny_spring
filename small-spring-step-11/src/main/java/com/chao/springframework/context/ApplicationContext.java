package com.chao.springframework.context;

import com.chao.springframework.beans.factory.HierarchicalBeanFactory;
import com.chao.springframework.beans.factory.ListableBeanFactory;
import com.chao.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
