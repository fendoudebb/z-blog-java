package com.msj.blog.service.article;

import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;
import com.msj.blog.entity.vo.article.ArticleModuleVo;
import com.msj.blog.repository.article.ArticleModuleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * zbj: create on 2018/08/07 14:44
 */
@Service
@CacheConfig(cacheNames = "article-module")
public class ArticleModuleServiceImpl implements ArticleModuleService {

    @Resource
    private ArticleModuleRepository articleModuleRepository;

    @Override
    public Optional<ArticleModule> findById(Long id) {
        return articleModuleRepository.findById(id);
    }

    @Override
    @Cacheable(key = "'article-all-module'")
    public List<ArticleModuleVo> findAllModule() {
        List<ArticleModule> articleModules = articleModuleRepository.findAll();
        if (articleModules.isEmpty()) {
            return Collections.emptyList();
        }
        List<ArticleModuleVo> articleModuleVos = new ArrayList<>();
        ArticleModuleVo articleModuleVo;
        for (ArticleModule articleModule : articleModules) {
            articleModuleVo = new ArticleModuleVo();
            BeanUtils.copyProperties(articleModule, articleModuleVo);
            ArrayList<ArticleCategoryVo> articleCategoryVos = new ArrayList<>();
            ArticleCategoryVo articleCategoryVo;
            for (ArticleCategory articleCategory : articleModule.getArticleCategories()) {
                articleCategoryVo = new ArticleCategoryVo();
                BeanUtils.copyProperties(articleCategory, articleCategoryVo);
                articleCategoryVos.add(articleCategoryVo);
            }
            articleModuleVo.setArticleCategoryVos(articleCategoryVos);
            articleModuleVos.add(articleModuleVo);
        }
        return articleModuleVos;
    }

}
