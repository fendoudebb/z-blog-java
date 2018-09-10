package com.msj.blog.controller.ui.article;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.dto.page.PageDto;
import com.msj.blog.entity.vo.article.ArticlePageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.SliceVo;
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
        String secondaryCategoryName = pageDto.getSecondaryCategoryName();
        if (secondaryCategoryName.isEmpty()) {
            secondaryCategoryName = "all";
        }
        Integer page = pageDto.getPage();
        Integer size = pageDto.getSize();
        articlePage = cacheService.getArticlePage(secondaryCategoryName, page, size);
        if (StringUtils.isEmpty(articlePage)) {
            SliceVo<ArticlePageVo> articlePageVo = articleService.findArticleListBySlice(secondaryCategoryName, page, size);
            if (articlePageVo != null) {
                articlePage = JSON.write(articlePageVo);
                if (!StringUtils.isEmpty(articlePage)) {
                    cacheService.setArticlePage(secondaryCategoryName, page, size, articlePage);
                }
            }
        }
        if (StringUtils.isEmpty(articlePage)) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        } else {
            return getResponse().data(JSON.parse(articlePage, SliceVo.class));
        }
    }

}
