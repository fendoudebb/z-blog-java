package com.msj.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * zbj: created on 2018/6/17 15:24.
 * 按以下顺序执行
 * ApplicationStartingEvent
 * ApplicationEnvironmentPreparedEvent
 * ApplicationPreparedEvent
 * ApplicationStartedEvent <= 新增的事件
 * ApplicationReadyEvent
 * ApplicationFailedEvent
 */
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyEvent: " + event.toString());
    }
}
