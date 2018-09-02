package com.msj.blog.service.article.impl;

import com.msj.blog.config.cache.CacheKey;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.service.redis.RedisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
            redisService.delAll(CacheKey.KEY_PREFIX_ARTICLE_PAGE);
        } else if (Objects.equals(article.getArticleProperty(), ArticleProperty.ABOUT_US)) {
            redisService.del(CacheKey.KEY_PREFIX_ARTICLE_ABOUT_US);
        } else if (Objects.equals(article.getArticleProperty(), ArticleProperty.DISCLAIMER)) {
            redisService.del(CacheKey.KEY_PREFIX_ARTICLE_DISCLAIMER);
        }
        return saveArticle;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Optional<Article> findByIdAndAuditStatusAndArticleProperty(Long id, AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findByIdAndAuditStatusEqualsAndArticlePropertyEquals(id, auditStatus, articleProperty);
    }

    @Override
    public Page<Article> findByPageAndArticleProperty(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByArticlePropertyEqualsOrderByIdDesc(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndArticlePropertyExclude(ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByArticlePropertyIsNotOrderByIdDesc(articleProperty, pageable);
    }

    @Override
    public Page<Article> findByPageAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEqualsOrderByIdDesc(auditStatus, articleProperty, pageable);
    }

    @Override
    public List<Article> findByAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty) {
        return articleRepository.findAllByAuditStatusEqualsAndArticlePropertyEquals(auditStatus, articleProperty);
    }

    
}
