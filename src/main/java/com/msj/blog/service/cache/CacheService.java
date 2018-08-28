package com.msj.blog.service.cache;

/**
 * zbj: created on 2018/8/28 19:58.
 */
public interface CacheService {

    String getArticleFromRedis(Long id);

}
