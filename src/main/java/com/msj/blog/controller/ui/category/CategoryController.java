package com.msj.blog.controller.ui.category;

import com.msj.blog.controller.BaseController;
import com.msj.blog.response.Response;
import com.msj.blog.service.category.SecondaryCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Resource
    private SecondaryCategoryService secondaryCategoryService;

    @PostMapping("/secondary")
    public Response secondaryCategory() {
        return null;
    }

}
