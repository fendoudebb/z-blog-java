package com.msj.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * zbj: created on 2018/6/17 20:21.
 */
@Slf4j
public class DemoEvent extends ApplicationEvent {
    private static final long serialVersionUID = 4357936004691031755L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DemoEvent(Object source) {
        super(source);
        log.info("demo event: " + source.toString());
    }
}
