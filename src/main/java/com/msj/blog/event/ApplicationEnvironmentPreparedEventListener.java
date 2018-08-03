package com.msj.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
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
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("ApplicationEnvironmentPreparedEvent: " + event.toString());
    }
}
