package com.msj.blog.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * zbj: created on 2018/6/10 22:40.
 * Async任务需单独写在一个类下,不能写在Controller,多个异步任务可写在一起
 */
@Component
public class DemoAsyncTask {

    @Async()
    public void sleep() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Async:" + Thread.currentThread().getName() + ":"+Thread.currentThread().getId());
    }

}
