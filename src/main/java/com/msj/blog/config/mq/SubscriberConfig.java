package com.msj.blog.config.mq;

import com.msj.blog.config.executor.AppExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/6/16 12:56.
 */
@Configuration
public class SubscriberConfig {

    @Resource
    private AppExecutor appExecutor;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //设置MQ执行所在的线程池
        container.setSubscriptionExecutor(appExecutor.getExecutor());
        container.setTaskExecutor(appExecutor.getExecutor());
        container.addMessageListener(listenerAdapter, new PatternTopic("phone"));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

}
