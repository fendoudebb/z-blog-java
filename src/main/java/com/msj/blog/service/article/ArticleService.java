package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;

public interface ArticleService {

    ArticleVo getArticleById(Long id);

    PageVo<ArticlePageVo> findArticleListByPage(Integer page, Integer size);

    ArticleVo findAboutUsArticle();

    ArticleVo findDisclaimerArticle();

    ArticleDto findArticleDto(Article article);

}
