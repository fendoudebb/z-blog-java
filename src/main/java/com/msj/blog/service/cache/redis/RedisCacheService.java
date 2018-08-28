package com.msj.blog.service.cache.redis;

import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;

/**
 * zbj: created on 2018/8/28 19:59.
 */
public interface RedisCacheService {

    String getArticle(Long id);

    void setArticle(Long id, ArticleVo articleVo);

    String getArticlePage(Long page, Long count);

    void setArticlePage(Long page, Long count, ArticlePageVo articlePageVo);

}
