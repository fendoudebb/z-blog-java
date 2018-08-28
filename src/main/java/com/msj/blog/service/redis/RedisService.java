package com.msj.blog.service.redis;

import java.io.Serializable;

/**
 * operate redis
 * zbj: create on 2018/08/17 14:37
 */
public interface RedisService {

    /**
     * set string value
     *
     * @param key redis string key
     * @return redis string value
     */
    String getValue(String key);

    /**
     * set string value
     *
     * @param key   redis string key
     * @param value redis string value
     */
    void setValue(String key, String value);

    /**
     * get zSet length
     *
     * @param key redis zSet key
     * @return redis zSet length
     */
    Long zCard(String key);

    /**
     * get zSet score
     *
     * @param key    redis zSet key
     * @param member redis zSet member
     * @return redis zSet score
     */
    Double zScore(String key, String member);

    /**
     * delete redis key
     *
     * @param key redis key
     * @return success or not
     */
    Boolean del(String key);

    /**
     * delete all key in pattern
     * @param keyPattern redis key pattern
     * @return number of success delete
     */
    Long delAll(String keyPattern);

    void set(String key, Serializable value);

    Serializable get(String key);

}
