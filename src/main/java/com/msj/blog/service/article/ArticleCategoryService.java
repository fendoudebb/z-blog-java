package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;

import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:38
 */
public interface ArticleCategoryService {

    Optional<ArticleCategory> findById(Long id);


}
