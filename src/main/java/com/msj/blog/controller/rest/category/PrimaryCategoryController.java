package com.msj.blog.controller.rest.category;

import com.msj.blog.controller.BaseRestController;
import com.msj.blog.response.Response;
import com.msj.blog.service.category.PrimaryCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/08/07 17:14
 */
@Slf4j
@RestController
@RequestMapping("/admin/primary")
@PreAuthorize(value = "hasRole('ADMIN')")
public class PrimaryCategoryController extends BaseRestController {

    @Resource
    private PrimaryCategoryService primaryCategoryService;

    @PostMapping("/category")
    public Response categoryList() {
        return getResponse().data(primaryCategoryService.findAllPrimaryCategories());
    }

}
