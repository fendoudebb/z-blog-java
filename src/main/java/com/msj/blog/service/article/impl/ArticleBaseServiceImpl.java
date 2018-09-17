package com.msj.blog.service.article.impl;

import com.msj.blog.config.cache.CacheKey;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.service.redis.RedisService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * zbj: created on 2018/8/28 21:52.
 */
@Service
public class ArticleBaseServiceImpl implements ArticleBaseService {

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private RedisService redisService;

    @Override
    public Article saveOrUpdate(Article article) {
        Article saveArticle = articleRepository.save(article);
        if (Objects.equals(article.getArticleProperty(), ArticleProperty.PUBLIC)) {
            redisService.del(CacheKey.KEY_PREFIX_ARTICLE_VO + article.getId());
            redisService.del(CacheKey.KEY_PREFIX_TEMPLATE_ARTICLE + article.getAuthor() + "-" + article.getId());
            redisService.delAll(CacheKey.KEY_PREFIX_ARTICLE_SLICE);
            redisService.delAll(CacheKey.KEY_PREFIX_TEMPLATE_INDEX);
        }
        return saveArticle;
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Article findByIdAndAuditStatusAndArticleProperty(Long id, AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findByIdAndAuditStatusEqualsAndArticlePropertyEquals(id, auditStatus, articleProperty);
    }

    @Override
    public Page<Article> findByPageAndArticleProperty(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        return articleRepository.findAllByArticlePropertyEquals(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndArticlePropertyExclude(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        return articleRepository.findAllByArticlePropertyIsNot(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, String secondaryCategoryName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (Objects.equals("all", secondaryCategoryName)) {
            return articleRepository.findByAuditStatusEqualsAndArticlePropertyEquals(auditStatus, articleProperty, pageable);
        } else {
            return articleRepository.findByAuditStatusEqualsAndArticlePropertyEqualsAndSecondaryCategory_NameEquals(auditStatus, articleProperty, secondaryCategoryName, pageable);
        }

    }

    @Override
    public Slice<Article> findBySliceAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, String secondaryCategoryName, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (Objects.equals("all", secondaryCategoryName)) {
            return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEquals(auditStatus, articleProperty, pageable);
        } else {
            return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEqualsAndSecondaryCategory_NameEquals(auditStatus, articleProperty, secondaryCategoryName, pageable);
        }
    }

}
