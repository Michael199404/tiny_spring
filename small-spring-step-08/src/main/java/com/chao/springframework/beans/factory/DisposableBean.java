package com.chao.springframework.beans.factory;

/**
 * 单例 bean 在容器创建完成前会进行创建并初始化，在容器销毁的时候进行销毁
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
