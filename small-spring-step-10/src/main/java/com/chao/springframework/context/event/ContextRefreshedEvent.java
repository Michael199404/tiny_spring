package com.chao.springframework.context.event;

import com.chao.springframework.context.ApplicationEvent;

/**
 * 用于监听的刷新
 */
public class ContextRefreshedEvent extends ApplicationEvent {


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
