package com.chao.springframework.context.event;

/**
 * 用于监听的关闭
 */
public class ContextClosedEvent extends ApplicationContextEvent {


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
