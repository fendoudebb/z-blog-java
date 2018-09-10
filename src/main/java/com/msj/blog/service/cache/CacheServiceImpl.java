package com.msj.blog.service.cache;

import com.msj.blog.config.cache.CacheKey;
import com.msj.blog.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/8/28 20:00.
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisService redisService;

    @Override
    public String getArticle(Long id) {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_VO + id);
    }

    @Override
    public void setArticle(Long id, String article) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_VO + id, article);
    }

    @Override
    public String getArticlePage(String secondaryCategoryName, Integer page, Integer count) {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_SLICE + secondaryCategoryName + "-" + page + "-" + count);
    }

    @Override
    public void setArticlePage(String secondaryCategoryName, Integer page, Integer count, String articlePage) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_SLICE + secondaryCategoryName + "-" + page + "-" + count, articlePage);
    }

    @Override
    public String getSecondaryCategoryNames(String primaryCategoryName) {
        return redisService.getValue(CacheKey.KEY_PREFIX_SECONDARY_CATEGORY_NAMES + primaryCategoryName);
    }

    @Override
    public void setSecondaryCategoryNames(String primaryCategoryName, String secondaryCategoryNames) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_SLICE + primaryCategoryName, secondaryCategoryNames);
    }

}
