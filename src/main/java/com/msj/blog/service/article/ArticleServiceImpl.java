package com.msj.blog.service.article;


import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.entity.vo.article.ArticleCategoryVo;
import com.msj.blog.entity.vo.article.ArticleModuleVo;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.repository.article.ArticleCategoryRepository;
import com.msj.blog.repository.article.ArticleModuleRepository;
import com.msj.blog.repository.article.ArticleRepository;
import com.msj.blog.util.MarkdownUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleCategoryRepository articleCategoryRepository;
    @Resource
    private ArticleModuleRepository articleModuleRepository;

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"article-page"}, allEntries = true)
    public boolean saveArticle(ArticleVo articleVo) {
        if (articleVo == null) {
            return false;
        }
        Article article = new Article();
        ArticleModule articleModule = articleModuleRepository.findById(articleVo.getArticleModuleId()).orElse(null);
        BeanUtils.copyProperties(articleVo, article);
        article.setArticleModule(articleModule);
        Article a = articleRepository.save(article);
        return a.getId() != null;
    }

    @Override
    @Cacheable(key = "'article-' + #p0")
    public Optional<ArticleVo> getById(Long id) {
        return findById(id).map(article -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setContent(MarkdownUtil.parse(article.getContent()));
            articleVo.setArticleModule(article.getArticleModule().getName());
            articleVo.setArticleModuleAlias(article.getArticleModule().getAlias());
            return articleVo;
        });
    }

    @Override
    @Cacheable(key = "'article-aboutus'")
    public ArticleVo findAboutUsArticle() {
        Article aboutUs = articleRepository.findAboutUs();
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(aboutUs, articleVo);
        articleVo.setContent(MarkdownUtil.parse(aboutUs.getContent()));
        return articleVo;
    }

    @Override
    @Cacheable(value = {"article-page"}, key = "'article-page-' + #p0 + '-' + #p1")
    public PageVo<ArticlePageVo> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> pages = articleRepository.findAllByOrderByUpdateTimeDesc(pageable);
        PageVo<ArticlePageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<Article> content = pages.getContent();
        List<ArticlePageVo> articlePageVos = new ArrayList<>();
        ArticlePageVo articlePageVo;
        for (Article article : content) {
            articlePageVo = new ArticlePageVo();
            ArticleModule articleModule = article.getArticleModule();
            if (articleModule != null) {
                articlePageVo.setArticleModule(articleModule.getName());
                articlePageVo.setArticleModuleAlias(articleModule.getAlias());
            }
            BeanUtils.copyProperties(article, articlePageVo);
            articlePageVos.add(articlePageVo);
        }
        pageVo.setContent(articlePageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        pageVo.setLastModifyTime(System.currentTimeMillis());
        return pageVo;
    }

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
