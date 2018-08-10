package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;
import com.msj.blog.entity.vo.article.ArticleModuleVo;
import com.msj.blog.repository.article.ArticleCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * zbj: create on 2018/08/07 14:38
 */
@Service
@CacheConfig(cacheNames = "article-category")
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Resource
    private ArticleCategoryRepository articleCategoryRepository;

    @Override
    @Cacheable(key = "'article-all-category'")
    public List<ArticleCategoryVo> findAllCategory() {
        List<ArticleCategory> articleCategories = articleCategoryRepository.findAll();
        if (articleCategories.isEmpty()) {
            return Collections.emptyList();
        }
        List<ArticleCategoryVo> articleCategoryVos = new ArrayList<>();
        ArticleCategoryVo articleCategoryVo;
        for (ArticleCategory articleCategory : articleCategories) {
            articleCategoryVo = new ArticleCategoryVo();
            BeanUtils.copyProperties(articleCategory, articleCategoryVo);
            ArrayList<ArticleModuleVo> articleModuleVos = new ArrayList<>();
            ArticleModuleVo articleModuleVo;
            for (ArticleModule articleModule : articleCategory.getArticleModules()) {
                articleModuleVo = new ArticleModuleVo();
                BeanUtils.copyProperties(articleModule, articleModuleVo);
                articleModuleVo.setCategoryName(articleModule.getArticleCategory().getName());
                articleModuleVos.add(articleModuleVo);
            }
            articleCategoryVo.setArticleModuleVos(articleModuleVos);
            articleCategoryVos.add(articleCategoryVo);
        }
        return articleCategoryVos;
    }
}
