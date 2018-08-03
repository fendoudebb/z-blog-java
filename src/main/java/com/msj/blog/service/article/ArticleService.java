package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    Optional<Article> findById(Long id);

    boolean saveArticle(ArticleVo articleVo);

    Optional<ArticleVo> getById(Long id);

    ArticleVo findAboutUsArticle();

    PageVo<ArticlePageVo> findByPage(Integer page, Integer size);

    List<ArticleCategoryVo> findAllCategory();
}
