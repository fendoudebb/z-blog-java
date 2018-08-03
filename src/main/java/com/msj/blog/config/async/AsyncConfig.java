package com.msj.blog.config.async;

import com.msj.blog.config.executor.AppExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * zbj: create on 2018/06/14 15:10
 * 默认配置了SimpleAsyncUncaughtExceptionHandler
 */
@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Resource
    private AppExecutor appExecutor;

    @Override
    public Executor getAsyncExecutor() {
        return appExecutor.getExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error(String.format("blog Unexpected error occurred invoking async method '%s'.", method), ex);
    }
}
