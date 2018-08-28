package com.msj.blog.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Double zScore(String key, String member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long delAll(String keyPattern) {
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
