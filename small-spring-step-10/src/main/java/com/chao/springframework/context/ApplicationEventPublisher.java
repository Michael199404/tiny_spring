package com.chao.springframework.context;

/**
 * ApplicationEventPublisher 是整个一个事件的发布接口，所有的事件都需要从这个接口发布出去
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
