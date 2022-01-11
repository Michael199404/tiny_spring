package com.chao.springframework.context;

import cn.hutool.core.bean.BeanException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeanException
     */
    void refresh() throws BeanException;

    void registerShutdownHook();

    void close();
}
