package com.msj.blog.config.scheduling;

import com.msj.blog.config.executor.AppExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/06/15 16:23
 * 设置定时任务的线程池
 */
@Configuration
public class SchedulingConfig implements SchedulingConfigurer {

    @Resource
    private AppExecutor appExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(appExecutor.getExecutor());
    }
}
