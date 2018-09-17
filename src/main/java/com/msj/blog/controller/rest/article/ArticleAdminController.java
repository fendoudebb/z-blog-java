package com.msj.blog.controller.rest.article;

import com.msj.blog.controller.BaseRestController;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.dto.article.ArticleDto;
import com.msj.blog.entity.dto.article.ArticleIdDto;
import com.msj.blog.entity.dto.article.AuditStatusDto;
import com.msj.blog.entity.dto.page.PageDto;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import com.msj.blog.service.article.ArticleAdminService;
import com.msj.blog.service.article.ArticleBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/article")
@PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
public class ArticleAdminController extends BaseRestController {

    @Resource
    private ArticleBaseService articleBaseService;
    @Resource
    private ArticleAdminService articleAdminService;

    @PostMapping(value = "/info")
    public Response info(@RequestBody @Valid ArticleIdDto articleIdDto) {
        Article article = articleBaseService.findById(articleIdDto.getArticleId());
        if (article == null) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        }
        ArticleDto articleDto = articleAdminService.findArticleDto(article);
        return getResponse().data(articleDto);
    }

    @PostMapping(value = "/draft")
    public Response draft(@RequestBody @Valid PageDto pageDto) {
        return getResponse().data(articleAdminService.findAdminArticleDraftByPage(pageDto.getPage(), pageDto.getSize()));
    }

    @PostMapping(value = "/list")
    public Response list(@RequestBody @Valid PageDto pageDto) {
        return getResponse().data(articleAdminService.findAdminArticleListByPage(pageDto.getPage(), pageDto.getSize()));
    }

    @PostMapping(value = "/save")
    public Response save(@RequestBody @Valid ArticleDto articleDto) {
        boolean saveResult = articleAdminService.saveArticle(articleDto);
        return saveResult ? getResponse().msg(MsgTable.SAVE_ARTICLE_SUCCESS) : getResponse().msg(MsgTable.SAVE_ARTICLE_FAILURE).fail();
    }

    @PostMapping(value = "/edit/{articleId}")
    public Response edit(@PathVariable Long articleId, @RequestBody @Valid ArticleDto articleDto) {
        log.info("id: " + articleId);
        Article article = articleBaseService.findById(articleId);
        if (article == null) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        }
        boolean editResult = articleAdminService.editArticle(articleDto, article);
        return editResult ? getResponse().msg(MsgTable.EDIT_ARTICLE_SUCCESS) : getResponse().msg(MsgTable.EDIT_ARTICLE_FAILURE).fail();
    }

    @PostMapping(value = "/audit")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public Response audit(@RequestBody @Valid AuditStatusDto auditStatusDto) {
        Article article = articleBaseService.findById(auditStatusDto.getArticleId());
        if (article == null) {
            return getResponse().msg(MsgTable.ARTICLE_NOT_EXIST).fail();
        }
        AuditStatus auditStatus = null;
        try {
            auditStatus = AuditStatus.valueOf(auditStatusDto.getAuditStatus());
        } catch (IllegalArgumentException e) {
            log.info("audit enum error constant: {}", e.getMessage());
        }
        article.setAuditStatus(auditStatus);
        articleBaseService.saveOrUpdate(article);
        return getResponse().msg(MsgTable.MODIFY_AUDIT_STATUS_SUCCESS);
    }

}
