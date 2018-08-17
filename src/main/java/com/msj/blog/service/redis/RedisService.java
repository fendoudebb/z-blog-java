package com.msj.blog.service.redis;

import java.io.Serializable;

/**
 * zbj: create on 2018/08/17 14:37
 */
public interface RedisService {

    boolean del(String key);

    long delAll(String keyPattern);

    void set(String key, Serializable value);

    Serializable get(String key);

}
