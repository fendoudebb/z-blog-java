package com.msj.blog.controller.ui.site;

import com.msj.blog.controller.BaseController;
import com.msj.blog.response.Response;
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
public class SiteController extends BaseController{

    @Resource
    private SiteService siteService;

    @RequestMapping(value = "/{name}")
    @ResponseBody
    public Response getSite(@PathVariable String name) {
        log.info("get {} site", name);
        return getResponse(name);
    }
}
