package com.msj.blog.controller.admin;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.Response;
import com.msj.blog.service.article.ArticleModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/08/07 17:14
 */
@Slf4j
@RequestMapping("/admin/module")
@RestController
public class ArticleModuleManageController extends BaseController{

    @Resource
    private ArticleModuleService articleModuleService;

    @PostMapping("/list")
    public Response categoryList() {
        return getResponse(articleModuleService.findAllModule());
    }

}
