package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;
import com.msj.blog.entity.vo.article.ArticleUIPageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    Optional<Article> findById(Long id);

    Optional<Article> findByIdAndAuditStatus(AuditStatus auditStatus, Long id);

    Page<Article> findByPage(Integer page, Integer size);

    Page<Article> findByPageAndAuditStatus(AuditStatus auditStatus, Integer page, Integer size);

    boolean saveArticle(ArticleDto articleDto);

    Optional<ArticleVo> getAdminArticleById(Long id);

    Optional<ArticleVo> getUIArticleById(Long id);

    ArticleVo findAboutUsArticle();

    PageVo<ArticleAdminPageVo> findAdminArticleByPage(Integer page, Integer size);

    PageVo<ArticleUIPageVo> findUIArticleByPage(Integer page, Integer size);

    List<ArticleCategoryVo> findAllCategory();
}
