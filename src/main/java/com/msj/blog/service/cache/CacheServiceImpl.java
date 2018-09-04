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
    public String getArticlePage(Integer page, Integer count) {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_PAGE + page + "-" + count);
    }

    @Override
    public void setArticlePage(Integer page, Integer count, String articlePage) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_PAGE + page + "-" + count, articlePage);
    }

    @Override
    public String getAboutUsArticle() {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_ABOUT_US);
    }

    @Override
    public void setAboutUsArticle(String aboutUsArticle) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_ABOUT_US, aboutUsArticle);
    }

    @Override
    public String getDisclaimerArticle() {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_DISCLAIMER);
    }

    @Override
    public void setDisclaimerArticle(String disclaimerArticle) {
        redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_DISCLAIMER, disclaimerArticle);
    }

}
