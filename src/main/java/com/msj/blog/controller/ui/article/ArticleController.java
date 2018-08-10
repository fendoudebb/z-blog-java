package com.msj.blog.controller.ui.article;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.service.article.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * zbj: create on 2018/06/06 18:00
 */
@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;

    @GetMapping(value = {"/{category}/{id}"})
    public String article(WebRequest webRequest, Model model, @PathVariable String category, @PathVariable Long id) {
        ArticleVo articleVo = articleService.getUIArticleById(id).orElse(null);
        if (articleVo == null || !Objects.equals(category, articleVo.getArticleCategory())) {
            return "error/404";
        }
        if (webRequest.checkNotModified(Timestamp.valueOf(articleVo.getUpdateTime()).getTime())) {
            return null;
        }
        model.addAttribute("article", articleVo);
        return "article/article";
    }


}
