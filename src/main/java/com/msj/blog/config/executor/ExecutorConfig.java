package com.msj.blog.config.executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 1.继承WebMvcConfigurerAdapter是为了Controller异步(类似Servlet3 NIO),配置线程池,否则无限开线程
 * <p>
 * 2.此类同样会时@EnableAsync异步任务使用此线程池
 * 2.1注意@Aysnc异步任务需要单独写在一个类中,否则还是在原来线程
 */
//@Configuration
public class ExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private static final int corePoolSize = 10;
    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private static final int maxPoolSize = 200;
    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private static final int queueCapacity = 10;

//    @Bean
    public Executor config() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("blog-thread-");
        // 设置拒绝策略
        /*executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // .....
            }
        });*/
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}