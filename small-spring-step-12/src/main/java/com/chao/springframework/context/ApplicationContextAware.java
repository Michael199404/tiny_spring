package com.chao.springframework.context;

import com.chao.springframework.beans.factory.Aware;

/**
 * 实现此接口，既能感知所属的 ApplicationContext
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationcontext);

}
