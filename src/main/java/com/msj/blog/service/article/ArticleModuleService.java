package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleModule;

import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:43
 */
public interface ArticleModuleService {

    Optional<ArticleModule> findById(Long id);

}
