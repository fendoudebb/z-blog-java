package com.msj.blog.service.article;

import com.msj.blog.entity.vo.article.ArticleCategoryVo;

import java.util.List;

/**
 * zbj: create on 2018/08/07 14:38
 */
public interface ArticleCategoryService {

    List<ArticleCategoryVo> findAllCategory();

}
