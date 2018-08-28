package com.msj.blog.service.cache;

import com.msj.blog.service.cache.redis.RedisCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/8/28 19:59.
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisCacheService redisCacheService;

    @Override
    public String getArticleFromRedis(Long id) {
        return redisCacheService.getArticle(id);
    }

}
