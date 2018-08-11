package com.msj.blog.controller.ui;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.article.ArticleUIPageVo;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.entity.vo.page.PageVo;
import com.msj.blog.service.article.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * zbj: create on 2018/06/05 15:21
 */
@Slf4j
@Controller
public class UIHomeController extends BaseController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/")
    public String index(WebRequest webRequest, Model model, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageVo<ArticleUIPageVo> articleVoPageVo = articleService.findUIArticleListByPage(page, size);
        if (webRequest.checkNotModified(articleVoPageVo.getLastModifyTime())) {
            return null;
        }
        List<ArticleUIPageVo> content = articleVoPageVo.getContent();
        if (content == null || content.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("articleVoPageVo", articleVoPageVo);
        return "index";
    }

    @GetMapping("/about")
    public String about(WebRequest webRequest, Model model) {
        ArticleVo articleVo = articleService.findAboutUsArticle().orElse(null);
        if (articleVo == null) {
            return "error/404";
        }
        if (webRequest.checkNotModified(Timestamp.valueOf(articleVo.getUpdateTime()).getTime())) {
            return null;
        }
        model.addAttribute("article", articleVo);
        model.addAttribute("showDetailInfo", true);
        return "article/article";
    }

}
