package com.msj.blog.task;

import com.msj.blog.config.executor.AppExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * zbj: create on 2018/06/15 16:17
 */
@Slf4j
@Component
public class DemoSchedulingTask {

    @Resource
    private AppExecutor appExecutor;


//    @Scheduled(fixedDelay = 30000)
    public void schedule() {
        ThreadPoolTaskScheduler executor = (ThreadPoolTaskScheduler) appExecutor.getExecutor();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = executor.getScheduledThreadPoolExecutor();
        log.info("{},,taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                executor.getThreadNamePrefix(),
                scheduledThreadPoolExecutor.getTaskCount(),
                scheduledThreadPoolExecutor.getCompletedTaskCount(),
                scheduledThreadPoolExecutor.getActiveCount(),
                scheduledThreadPoolExecutor.getQueue().size());

    }

}
