package com.msj.blog.service.article;

import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.entity.vo.page.SliceVo;

public interface ArticleService {

    ArticleVo findArticleById(Long id);

    PageVo<ArticlePageVo> findArticleListByPage(Integer page, Integer size);

    SliceVo<ArticlePageVo> findArticleListBySlice(String secondaryCategoryName, Integer page, Integer size);

}
