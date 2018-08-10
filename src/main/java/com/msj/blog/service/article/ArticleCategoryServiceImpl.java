package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.repository.article.ArticleCategoryRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:38
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Resource
    private ArticleCategoryRepository articleCategoryRepository;

    @Override
    public Optional<ArticleCategory> findById(Long id) {
        return articleCategoryRepository.findById(id);
    }


}
