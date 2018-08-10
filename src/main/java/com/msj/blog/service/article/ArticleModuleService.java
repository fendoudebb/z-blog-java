package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.entity.vo.article.ArticleModuleVo;

import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:43
 */
public interface ArticleModuleService {

    Optional<ArticleModule> findById(Long id);

    List<ArticleModuleVo> findAllModule();

}
