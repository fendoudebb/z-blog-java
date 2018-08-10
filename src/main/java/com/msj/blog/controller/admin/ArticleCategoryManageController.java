package com.msj.blog.controller.admin;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.Response;
import com.msj.blog.service.article.ArticleCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/08/07 17:14
 */
@Slf4j
@RequestMapping("/admin/category")
@RestController
public class ArticleCategoryManageController extends BaseController{

    @Resource
    private ArticleCategoryService articleCategoryService;

    @PostMapping("/list")
    public Response categoryList() {
        return getResponse(articleCategoryService.findAllCategory());
    }

}
