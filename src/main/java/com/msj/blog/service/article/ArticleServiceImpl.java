package com.msj.blog.service.article;


import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.article.ArticleCategory;
import com.msj.blog.entity.domain.article.ArticleModule;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.*;
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
    public Optional<Article> findByIdAndAuditStatus(AuditStatus auditStatus, Long id) {
        return articleRepository.findByAuditStatusEqualsAndId(auditStatus, id);
    }

    @Override
    public Page<Article> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByOrderByUpdateTimeDesc(pageable);
    }

    @Override
    public Page<Article> findByPageAndAuditStatus(AuditStatus auditStatus, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findAllByAuditStatusEqualsOrderByUpdateTimeDesc(auditStatus, pageable);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"article-page-ui"}, allEntries = true)
    public boolean saveArticle(ArticleDto articleDto) {
        if (articleDto == null) {
            return false;
        }
        Article article = new Article();
        ArticleModule articleModule = articleModuleRepository.findById(articleDto.getArticleModuleId()).orElse(null);
        BeanUtils.copyProperties(articleDto, article);
        article.setArticleModule(articleModule);
        Article a = articleRepository.save(article);
        return a.getId() != null;
    }

    @Override
    public Optional<ArticleVo> getAdminArticleById(Long id) {
        return findById(id).map(this::transferArticle2ArticleVo);
    }

    @Override
    @Cacheable(key = "'article-ui-' + #p0")
    public Optional<ArticleVo> getUIArticleById(Long id) {
        return findByIdAndAuditStatus(AuditStatus.online, id).map(this::transferArticle2ArticleVo);
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
    public PageVo<ArticleAdminPageVo> findAdminArticleByPage(Integer page, Integer size) {
        Page<Article> pages = findByPage(page, size);
        PageVo<ArticleAdminPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleAdminPageVo> articleAdminPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleAdminPageVo articleAdminPageVo = new ArticleAdminPageVo();
            BeanUtils.copyProperties(article, articleAdminPageVo);
            ArticleModule articleModule = article.getArticleModule();
            if (articleModule != null) {
                articleAdminPageVo.setCategory(articleModule.getName());
            }
            articleAdminPageVo.setAuditStatus(article.getAuditStatus().getType());
            articleAdminPageVos.add(articleAdminPageVo);
        });
        pageVo.setContent(articleAdminPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        return pageVo;
    }

    @Override
    @Cacheable(value = {"article-page-ui"}, key = "'article-page-ui-' + #p0 + '-' + #p1")
    public PageVo<ArticleUIPageVo> findUIArticleByPage(Integer page, Integer size) {
        Page<Article> pages = findByPageAndAuditStatus(AuditStatus.online, page, size);
        PageVo<ArticleUIPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleUIPageVo> articleUIPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleUIPageVo articleUIPageVo = new ArticleUIPageVo();
            ArticleModule articleModule = article.getArticleModule();
            BeanUtils.copyProperties(article, articleUIPageVo);
            if (articleModule != null) {
                articleUIPageVo.setArticleModule(articleModule.getName());
                articleUIPageVo.setArticleModuleAlias(articleModule.getAlias());
            }
            articleUIPageVos.add(articleUIPageVo);
        });
        pageVo.setContent(articleUIPageVos);
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

    private ArticleVo transferArticle2ArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setContent(MarkdownUtil.parse(article.getContent()));
        articleVo.setArticleModule(article.getArticleModule().getName());
        articleVo.setArticleModuleAlias(article.getArticleModule().getAlias());
        return articleVo;
    }
}
