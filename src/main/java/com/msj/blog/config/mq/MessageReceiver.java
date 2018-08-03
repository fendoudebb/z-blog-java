package com.msj.blog.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * zbj: created on 2018/6/16 21:57.
 */
@Slf4j
@Component
public class MessageReceiver {

    //接收消息的方法
    public void receiveMessage(String message){
        //这里处理其他逻辑
        log.info("receive message: " + message);
    }

}
