package com.msj.blog.controller.ui;

import com.msj.blog.config.cache.CacheKey;
import com.msj.blog.controller.BaseWebController;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.domain.site.Site;
import com.msj.blog.service.article.ArticleBaseService;
import com.msj.blog.service.redis.RedisService;
import com.msj.blog.service.site.SiteService;
import com.msj.blog.util.MarkdownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * zbj: create on 2018/09/13 10:12
 */
@Slf4j
@RestController
public class RouterController extends BaseWebController {

    @Resource
    private RedisService redisService;
    @Resource
    private ArticleBaseService articleBaseService;
    @Resource
    private SiteService siteService;

    @GetMapping(value = "/")
    public String index(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        String html = redisService.getValue(CacheKey.KEY_PREFIX_TEMPLATE_INDEX + page + "-" + size);
        if (StringUtils.isEmpty(html)) {
            Page<Article> pageArticle = articleBaseService.findByPageAndAuditStatusAndArticleProperty(AuditStatus.ONLINE, ArticleProperty.PUBLIC, "all", page, size);
            html = process("index", getWebContext("page", pageArticle));
            if (StringUtils.isEmpty(html)) {
                html = process("error/404", getWebContext());
            }
            redisService.setValue(CacheKey.KEY_PREFIX_TEMPLATE_INDEX + page + "-" + size, html);
        }
        return html;
    }

    @GetMapping("/{author}/{id}")
    public String id(@PathVariable String author, @PathVariable Long id) {
        String html = redisService.getValue(CacheKey.KEY_PREFIX_TEMPLATE_ARTICLE + author + "-" + id);
        if (StringUtils.isEmpty(html)) {
            Article article = articleBaseService.findById(id);
            if (article != null) {
                article.setContent(MarkdownUtil.parse(article.getContent()));
                html = process("article/article", getWebContext("article", article));
            }
            if (StringUtils.isEmpty(html)) {
                html = process("error/404", getWebContext());
            }
            redisService.setValue(CacheKey.KEY_PREFIX_TEMPLATE_ARTICLE + author + "-" + id, html);
        }
        return html;
    }

    @GetMapping("/site")
    public String site() {
        String html = redisService.getValue(CacheKey.KEY_PREFIX_TEMPLATE + "site");
        if (StringUtils.isEmpty(html)) {
            Map<String, List<Site>> siteMap = siteService.findAll();
            html = process("site/site", getWebContext("siteMap", siteMap));
            if (StringUtils.isEmpty(html)) {
                html = process("error/404", getWebContext());
            }
            redisService.setValue(CacheKey.KEY_PREFIX_TEMPLATE + "site", html);
        }
        return html;
    }

    @GetMapping("/format/json")
    public String json() {
        String html = redisService.getValue(CacheKey.KEY_PREFIX_TEMPLATE + "format/json");
        if (StringUtils.isEmpty(html)) {
            html = process("tool/format_json", getWebContext());
            if (StringUtils.isEmpty(html)) {
                html = process("error/404", getWebContext());
            }
            redisService.setValue(CacheKey.KEY_PREFIX_TEMPLATE + "format/json", html);
        }
        return html;
    }

}
