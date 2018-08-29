package com.msj.blog.service.redis;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Set;

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
     * set zSet member's score
     *
     * @param key    redis zSet key
     * @param member redis zSet key's member
     * @param score  redis zSet member's score
     * @return operate success or not
     */
    Boolean zAdd(String key, String member, double score);

    /**
     * increment zSet member's delta score
     *
     * @param key    redis zSet key
     * @param member redis zSet key's member
     * @param delta  redis zSet member's delta score
     * @return current zSet member's score
     */
    Double zIncrBy(String key, String member, double delta);

    /**
     * reverse range zSet
     *
     * @param key   redis zSet key
     * @param start range start index
     * @param end   range end index
     * @return range key's member set
     */
    Set<String> zRevRange(String key, long start, long end);

    /**
     * reverse range zSet with scores
     *
     * @param key   redis zSet key
     * @param start range start index
     * @param end   range end index
     * @return range key' member set with scores
     */
    Set<TypedTuple<String>> zRevRangeWithScores(String key, long start, long end);

    /**
     * get zSet score
     *
     * @param key    redis zSet key
     * @param member redis zSet key's member
     * @return redis zSet score
     */
    Double zScore(String key, String member);

    /**
     * get zSet length
     *
     * @param key redis zSet key
     * @return redis zSet length
     */
    Long zCard(String key);

    /**
     * delete redis key
     *
     * @param key redis key
     * @return operate success or not
     */
    Boolean del(String key);

    /**
     * delete all key in pattern
     *
     * @param keyPattern redis key pattern
     * @return success delete numbers
     */
    Long delAll(String keyPattern);

}
