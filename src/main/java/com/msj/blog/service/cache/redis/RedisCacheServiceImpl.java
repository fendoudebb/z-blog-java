package com.msj.blog.service.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msj.blog.config.cache.CacheKey;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/8/28 20:00.
 */
@Slf4j
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @Resource
    private RedisService redisService;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String getArticle(Long id) {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_VO + id);
    }

    @Override
    public void setArticle(Long id, ArticleVo articleVo) {
        try {
            redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_VO, objectMapper.writeValueAsString(articleVo));
        } catch (JsonProcessingException e) {
            log.error("setArticle json transfer exception:[{}]", e.getMessage());
        }
    }

    @Override
    public String getArticlePage(Long page, Long count) {
        return redisService.getValue(CacheKey.KEY_PREFIX_ARTICLE_PAGE + page + "-" + count);
    }

    @Override
    public void setArticlePage(Long page, Long count, ArticlePageVo articlePageVo) {
        try {
            redisService.setValue(CacheKey.KEY_PREFIX_ARTICLE_PAGE + page + "-" + count,objectMapper.writeValueAsString(articlePageVo));
        } catch (JsonProcessingException e) {
            log.error("setArticlePage json transfer exception:[{}]", e.getMessage());
        }
    }
}
