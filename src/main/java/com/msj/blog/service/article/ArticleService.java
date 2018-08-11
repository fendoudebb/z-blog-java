package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.article.ArticleUIPageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.stream.Stream;

public interface ArticleService {

    Article saveOrUpdate(Article article);

    Optional<Article> findById(Long id);

    Optional<Article> findByIdAndAuditStatusAndArticleProperty(Long id, AuditStatus auditStatus, ArticleProperty articleProperty);

    Page<Article> findByPage(Integer page, Integer size);

    Page<Article> findByPageAndAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty, Integer page, Integer size);

    Stream<Article> findByAuditStatusAndArticleProperty(AuditStatus auditStatus, ArticleProperty articleProperty);

    boolean saveArticle(ArticleDto articleDto);

    Optional<ArticleVo> getAdminArticleById(Long id);

    Optional<ArticleVo> getUIArticleById(Long id);

    PageVo<ArticleAdminPageVo> findAdminArticleByPage(Integer page, Integer size);

    PageVo<ArticleUIPageVo> findUIArticleByPage(Integer page, Integer size);

    Optional<ArticleVo> findAboutUsArticle();

}
