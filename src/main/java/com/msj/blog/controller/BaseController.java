package com.msj.blog.controller;


import com.msj.blog.response.Response;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zbj: created on 2018/6/2 8:59.
 */
public class BaseController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    protected <T> Response<T> getResponse() {
        Response<T> response = new Response<>();
        return response;
    }

}
