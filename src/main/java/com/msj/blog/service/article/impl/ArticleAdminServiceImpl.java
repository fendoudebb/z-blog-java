package com.msj.blog.service.article.impl;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.vo.article.ArticleAdminPageVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.service.article.ArticleAdminService;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.service.category.SecondaryCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * zbj: created on 2018/8/28 21:54.
 */
@Slf4j
@Service
public class ArticleAdminServiceImpl implements ArticleAdminService {

    @Resource
    private ArticleBaseService articleBaseService;
    @Resource
    private SecondaryCategoryService secondaryCategoryService;

    @Override
    public boolean saveArticle(ArticleDto articleDto) {
        if (articleDto == null) {
            return false;
        }
        Article article = transferArticleDto2Article(articleDto, null);
        if (article == null) {
            return false;
        }
        Article a = articleBaseService.saveOrUpdate(article);
        return a.getId() != null;
    }

    @Override
    public boolean editArticle(ArticleDto articleDto, Article article) {
        if (articleDto == null) {
            return false;
        }
        if (article == null) {
            return false;
        }
        article = transferArticleDto2Article(articleDto, article);
        if (article == null) {
            return false;
        }
        Article a = articleBaseService.saveOrUpdate(article);
        return a.getId() != null;
    }

    @Override
    public PageVo<ArticleAdminPageVo> findAdminArticleDraftByPage(Integer page, Integer size) {
        Page<Article> pages = articleBaseService.findByPageAndArticleProperty(ArticleProperty.DRAFT, page, size);
        return transferArticlePage2ArticleAdminPage(pages);
    }

    @Override
    public PageVo<ArticleAdminPageVo> findAdminArticleListByPage(Integer page, Integer size) {
        Page<Article> pages = articleBaseService.findByPageAndArticlePropertyExclude(ArticleProperty.DRAFT, page, size);
        return transferArticlePage2ArticleAdminPage(pages);
    }

    @Override
    public ArticleDto findArticleDto(Article article) {
        return transferArticle2ArticleDto(article);
    }

    private PageVo<ArticleAdminPageVo> transferArticlePage2ArticleAdminPage(Page<Article> pages) {
        PageVo<ArticleAdminPageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticleAdminPageVo> articleAdminPageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticleAdminPageVo articleAdminPageVo = new ArticleAdminPageVo();
            BeanUtils.copyProperties(article, articleAdminPageVo);
            SecondaryCategory secondaryCategory = article.getSecondaryCategory();
            if (secondaryCategory != null) {
                articleAdminPageVo.setCategory(secondaryCategory.getName());
            }
            articleAdminPageVo.setAuditStatus(article.getAuditStatus().name());
            articleAdminPageVos.add(articleAdminPageVo);
        });
        pageVo.setContent(articleAdminPageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        return pageVo;
    }

    private Article transferArticleDto2Article(ArticleDto articleDto, Article article) {
        if (article == null) {
            article = new Article();
        }
        SecondaryCategory secondaryCategory = secondaryCategoryService.findByName(articleDto.getCategory()).orElse(null);
        if (secondaryCategory == null) {
            return null;
        }
        BeanUtils.copyProperties(articleDto, article);
        article.setSecondaryCategory(secondaryCategory);
        try {
            ArticleProperty articleProperty = ArticleProperty.valueOf(articleDto.getArticleProperty());
            article.setArticleProperty(articleProperty);
        } catch (IllegalArgumentException e) {
            log.info("enum error constant: {}", e.getMessage());
            return null;
        }
        return article;
    }

    private ArticleDto transferArticle2ArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article, articleDto);
        articleDto.setCategory(article.getSecondaryCategory().getName());
        articleDto.setArticleProperty(article.getArticleProperty().name());
        return articleDto;
    }

}
