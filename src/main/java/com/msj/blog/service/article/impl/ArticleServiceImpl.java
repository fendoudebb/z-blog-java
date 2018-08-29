package com.msj.blog.service.article.impl;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.service.article.ArticleService;
import com.msj.blog.service.cache.redis.RedisCacheService;
import com.msj.blog.util.MarkdownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleBaseService articleBaseService;
    @Resource
    private RedisCacheService redisCacheService;

    @Override
    public ArticleVo findArticleById(Long id) {
        return articleBaseService.findByIdAndAuditStatusAndArticleProperty(id, AuditStatus.ONLINE, ArticleProperty.PUBLIC).map(this::transferArticle2ArticleVo).orElse(null);
    }

    @Override
    public PageVo<ArticlePageVo> findArticleListByPage(Integer page, Integer size) {
        Page<Article> pages = articleBaseService.findByPageAndAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.PUBLIC, page, size);
        PageVo<ArticlePageVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(pages.getTotalElements());
        pageVo.setTotalPages(pages.getTotalPages());
        List<ArticlePageVo> articlePageVos = new ArrayList<>();
        pages.forEach(article -> {
            ArticlePageVo articlePageVo = new ArticlePageVo();
            SecondaryCategory secondaryCategory = article.getSecondaryCategory();
            BeanUtils.copyProperties(article, articlePageVo);
            if (secondaryCategory != null) {
                articlePageVo.setCategory(secondaryCategory.getName());
                articlePageVo.setCategoryAlias(secondaryCategory.getAlias());
            }
            articlePageVos.add(articlePageVo);
        });
        pageVo.setContent(articlePageVos);
        pageVo.setNumber(pages.getNumber());
        pageVo.setNumberOfElements(pages.getNumberOfElements());
        pageVo.setSize(pages.getSize());
        pageVo.setLastModifyTime(System.currentTimeMillis());
        return pageVo;
    }

    @Override
    public ArticleVo findAboutUsArticle() {
        return transfer2SystemArticleVo(ArticleProperty.ABOUT_US);
    }

    @Override
    public ArticleVo findDisclaimerArticle() {
        return transfer2SystemArticleVo(ArticleProperty.DISCLAIMER);
    }


    private ArticleVo transfer2SystemArticleVo(ArticleProperty articleProperty) {
        return articleBaseService.findByAuditStatusAndArticleProperty(AuditStatus.ONLINE, articleProperty)
                .stream()
                .findFirst()
                .map(article -> {
                    ArticleVo articleVo = new ArticleVo();
                    BeanUtils.copyProperties(article, articleVo);
                    articleVo.setContent(MarkdownUtil.parse(article.getContent()));
                    articleVo.setCategoryAlias(article.getSecondaryCategory().getAlias());
                    return articleVo;
                }).orElse(null);
    }

    private ArticleVo transferArticle2ArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setContent(MarkdownUtil.parse(article.getContent()));
        SecondaryCategory secondaryCategory = article.getSecondaryCategory();
        if (secondaryCategory != null) {
            articleVo.setCategory(secondaryCategory.getName());
            articleVo.setCategoryAlias(secondaryCategory.getAlias());
        }
        return articleVo;
    }

}
