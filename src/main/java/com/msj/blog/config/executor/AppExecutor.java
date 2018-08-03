package com.msj.blog.config.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * zbj: created on 2018/6/17 10:09.
 */
@Slf4j
@Component
public class AppExecutor {

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public Executor getExecutor() {
        if (threadPoolTaskScheduler != null) {
            return threadPoolTaskScheduler;
        }
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setThreadNamePrefix("blog-thread-");
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        scheduler.initialize();
        threadPoolTaskScheduler = scheduler;
        return threadPoolTaskScheduler;
    }

}
