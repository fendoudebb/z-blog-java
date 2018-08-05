package com.msj.blog.controller.admin;

import com.msj.blog.controller.BaseController;
import com.msj.blog.entity.vo.Response;
import com.msj.blog.entity.vo.article.ArticleVo;
import com.msj.blog.service.article.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/admin/article")
public class ArticleManageController extends BaseController {

    @Resource
    private ArticleService articleService;

    @PostMapping(value = "/list")
    public Response page(@RequestParam(value = "page", defaultValue = "0") Integer number, @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return getResponse(articleService.findByPage(number, size));
    }

    @PostMapping(value = "/save")
    public Response save(@RequestBody ArticleVo articleVo) {
        Response<String> response = new Response<>();
        if (articleVo == null) {
            response.setData("0");
            log.info("article is null");
            return response;
        }
        boolean saveResult = articleService.saveArticle(articleVo);
        if (saveResult) {
            response.setData("1");
        } else {
            response.setData("0");
        }
        return response;
    }

}
