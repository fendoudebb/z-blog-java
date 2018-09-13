package com.msj.blog.controller.ui;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.util.MarkdownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/09/13 10:12
 */
@Slf4j
@Controller
public class RouterController {

    @Resource
    private ArticleBaseService articleBaseService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "3") Integer size) {
        Page<Article> pageArticle = articleBaseService.findByPageAndAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.PUBLIC, "all", page, size);
        model.addAttribute("page", pageArticle);
        return "index";
    }

    @GetMapping("/{author}/{id}")
    public String id(Model model, @PathVariable String author, @PathVariable Long id) {
        Article article = articleBaseService.findById(id);
        article.setContent(MarkdownUtil.parse(article.getContent()));
        model.addAttribute("article", article);
        return "article/article";
    }

}
