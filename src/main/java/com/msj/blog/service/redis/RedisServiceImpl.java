package com.msj.blog.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 * zbj: create on 2018/08/17 14:37
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public long delAll(String keyPattern) {
        Set<String> keys = redisTemplate.keys(keyPattern + "*");
        return redisTemplate.delete(keys);
    }

    @Override
    public void set(String key, Serializable value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Serializable get(String key) {
       return redisTemplate.opsForValue().get(key);
    }

}
