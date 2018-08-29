package com.msj.blog.controller.ui.article;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.response.Response;
import com.msj.blog.service.article.ArticleService;
import com.msj.blog.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.sql.Timestamp;

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


    @GetMapping(value = {"/{id}"})
    public Response article(@PathVariable Long id) {
        String article;
        article = cacheService.getArticleFromRedis(id);
        if (StringUtils.isEmpty(article)) {
            ArticleVo articleVo = articleService.findArticleById(id);
        }
//        ArticleVo articleVo = articleService.findArticleById(id);
        return getResponse(article);
    }

    @GetMapping("/about")
    public String about(WebRequest webRequest, Model model) {
        ArticleVo articleVo = articleService.findAboutUsArticle();
        return view(articleVo, webRequest, model);
    }

    @GetMapping("/disclaimer")
    public String disclaimer(WebRequest webRequest, Model model) {
        ArticleVo articleVo = articleService.findDisclaimerArticle();
        return view(articleVo, webRequest, model);
    }

    private String view(ArticleVo articleVo, WebRequest webRequest, Model model) {
        if (articleVo == null) {
            return "error/404";
        }
        if (webRequest.checkNotModified(Timestamp.valueOf(articleVo.getUpdateTime()).getTime())) {
            return null;
        }
        model.addAttribute("article", articleVo);
        return "article/article";
    }


}
