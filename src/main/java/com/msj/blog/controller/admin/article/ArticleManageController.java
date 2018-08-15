package com.msj.blog.controller.admin.article;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.dto.article.AuditStatusDto;
import com.msj.blog.entity.dto.page.PageDto;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import com.msj.blog.service.article.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("/admin/article")
public class ArticleManageController extends BaseController {

    @Resource
    private ArticleService articleService;

    @PostMapping(value = "/draft")
    public Response draft(@RequestBody @Valid PageDto pageDto) {
        return getResponse(articleService.findAdminArticleDraftByPage(pageDto.getPage(), pageDto.getSize()));
    }

    @PostMapping(value = "/list")
    public Response list(@RequestBody @Valid PageDto pageDto) {
        return getResponse(articleService.findAdminArticleListByPage(pageDto.getPage(), pageDto.getSize()));
    }

    @PostMapping(value = "/save")
    public Response save(@RequestBody @Valid ArticleDto articleDto) {
        boolean saveResult = articleService.saveArticle(articleDto);
        return saveResult ? getResponse(MsgTable.SAVE_ARTICLE_SUCCESS) : getResponse(MsgTable.SAVE_ARTICLE_FAILURE).fail();
    }

    @PostMapping(value = "/edit/{articleId}")
    public Response edit(@PathVariable Long articleId,@RequestBody @Valid ArticleDto articleDto) {
        log.info("id: " + articleId);
        return getResponse(articleId);
    }

    @PostMapping(value = "/audit")
    public Response audit(@RequestBody @Valid AuditStatusDto auditStatusDto) {
        Article article = articleService.findById(auditStatusDto.getArticleId()).orElse(null);
        if (article == null) {
            return getResponse(MsgTable.ARTICLE_NOT_EXIST).fail();
        }
        AuditStatus auditStatus = null;
        try {
            auditStatus = AuditStatus.valueOf(auditStatusDto.getAuditStatus());
        } catch (IllegalArgumentException e) {
            log.info("audit enum error constant: {}", e.getMessage());
        }
        article.setAuditStatus(auditStatus);
        articleService.saveOrUpdate(article);
        return getResponse(MsgTable.MODIFY_AUDIT_STATUS_SUCCESS);
    }

    @GetMapping(value = "/preview/audit/{id}")
    public ModelAndView articlePreviewAudit(WebRequest webRequest, @PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        ArticleVo articleVo = articleService.getAdminArticleById(id).orElse(null);
        if (articleVo == null) {
            mv.setViewName("error/404");
            return mv;
        }
        if (webRequest.checkNotModified(Timestamp.valueOf(articleVo.getUpdateTime()).getTime())) {
            return null;
        }
        mv.setViewName("article/article");
        mv.addObject("article", articleVo);
        return mv;
    }


}
