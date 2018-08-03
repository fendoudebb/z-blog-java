package com.msj.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * zbj: created on 2018/6/17 20:13.
 */
@Slf4j
@Component
public class DemoEventListener {

    @Async
    @EventListener
    public void demoEvent(DemoEvent event) {
        Object source = event.getSource();
        log.info("demo event listener: " + source.toString());
    }

}
