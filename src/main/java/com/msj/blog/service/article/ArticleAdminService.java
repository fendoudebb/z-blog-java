package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.page.PageVo;

/**
 * zbj: created on 2018/8/28 21:54.
 */
public interface ArticleAdminService {

    boolean saveArticle(ArticleDto articleDto);

    boolean editArticle(ArticleDto articleDto, Article article);

    PageVo<ArticleAdminPageVo> findAdminArticleDraftByPage(Integer page, Integer size);

    PageVo<ArticleAdminPageVo> findAdminArticleListByPage(Integer page, Integer size);

}
