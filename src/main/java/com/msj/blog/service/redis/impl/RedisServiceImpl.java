package com.msj.blog.service.redis.impl;

import com.msj.blog.service.redis.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * zbj: create on 2018/08/17 14:37
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Boolean zAdd(String key, String member, double score) {
        return stringRedisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public Double zIncrBy(String key, String member, double delta) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, member, delta);
    }

    @Override
    public Set<String> zRevRange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    @Override
    public Set<TypedTuple<String>> zRevRangeWithScores(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    @Override
    public Double zScore(String key, String member) {
        return stringRedisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public Long zCard(String key) {
        return stringRedisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public Long delAll(String keyPattern) {
        Set<String> keys = stringRedisTemplate.keys(keyPattern + "*");
        return stringRedisTemplate.delete(keys);
    }

}
