package com.msj.blog.controller.ui.site;

import com.msj.blog.entity.vo.Response;
import com.msj.blog.entity.vo.site.SiteCategoryVo;
import com.msj.blog.service.site.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/4/14 11:01.
 */
@Slf4j
@Controller
@RequestMapping("/site")
public class SiteController {

    @Resource
    private SiteService siteService;

    @RequestMapping(value = "/{name}")
    @ResponseBody
    public Response getSite(@PathVariable String name) {
        log.info("get {} site", name);
        Response<SiteCategoryVo> response = new Response<>();
        SiteCategoryVo siteCategoryVo = siteService.getSiteCategoryVo(name);
        response.setData(siteCategoryVo);
        return response;
    }
}
