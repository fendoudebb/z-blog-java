package com.msj.blog.controller;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zbj: create on 2018/09/17 11:28
 */

public class BaseWebController {

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    protected TemplateEngine templateEngine;

    protected WebContext getWebContext() {
        return new WebContext(request, response, request.getServletContext());
    }

    protected WebContext getWebContext(String name, Object value) {
        WebContext context = getWebContext();
        context.setVariable(name, value);
        return context;
    }

    protected String process(String template, WebContext context) {
        return templateEngine.process(template, context);
    }



}
