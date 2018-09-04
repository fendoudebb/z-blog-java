package com.msj.blog.controller.ui.article;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.dto.page.PageDto;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import com.msj.blog.service.article.ArticleService;
import com.msj.blog.service.cache.CacheService;
import com.msj.blog.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * zbj: create on 2018/06/06 18:00
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Resource
    private CacheService cacheService;
    @Resource
    private ArticleService articleService;

    @PostMapping(value = {"/{id}"})
    public Response article(@PathVariable Long id) {
        String article;
        article = cacheService.getArticle(id);
        if (StringUtils.isEmpty(article)) {
            ArticleVo articleVo = articleService.findArticleById(id);
            if (articleVo != null) {
                article = JSON.write(articleVo);
                if (!StringUtils.isEmpty(article)) {
                    cacheService.setArticle(articleVo.getId(), article);
                }
            }
        }
        if (StringUtils.isEmpty(article)) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(article, ArticleVo.class));
        }
    }

    @PostMapping("/list")
    public Response list(@RequestBody @Valid PageDto pageDto) {
        String articlePage;
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        articlePage = cacheService.getArticlePage(page, size);
        if (StringUtils.isEmpty(articlePage)) {
            PageVo<ArticlePageVo> articlePageVo = articleService.findArticleListByPage(page, size);
            if (articlePageVo != null) {
                articlePage = JSON.write(articlePageVo);
                if (!StringUtils.isEmpty(articlePage)) {
                    cacheService.setArticlePage(page, size, articlePage);
                }
            }
        }
        if (StringUtils.isEmpty(articlePage)) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(articlePage, PageVo.class));
        }
    }

    @PostMapping("/about")
    public Response about() {
        String aboutArticle;
        aboutArticle = cacheService.getAboutUsArticle();
        if (StringUtils.isEmpty(aboutArticle)) {
            ArticleVo articleVo = articleService.findAboutUsArticle();
            if (articleVo != null) {
                aboutArticle = JSON.write(articleVo);
                if (!StringUtils.isEmpty(aboutArticle)) {
                    cacheService.setArticle(articleVo.getId(), aboutArticle);
                }
            }
        }
        if (StringUtils.isEmpty(aboutArticle)) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(aboutArticle, ArticleVo.class));
        }
    }

    @PostMapping("/disclaimer")
    public Response disclaimer() {
        String disclaimerArticle;
        disclaimerArticle = cacheService.getDisclaimerArticle();
        if (StringUtils.isEmpty(disclaimerArticle)) {
            ArticleVo articleVo = articleService.findDisclaimerArticle();
            if (articleVo != null) {
                disclaimerArticle = JSON.write(articleVo);
                if (!StringUtils.isEmpty(disclaimerArticle)) {
                    cacheService.setArticle(articleVo.getId(), disclaimerArticle);
                }
            }
        }
        if (StringUtils.isEmpty(disclaimerArticle)) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(disclaimerArticle, ArticleVo.class));
        }
    }

}
