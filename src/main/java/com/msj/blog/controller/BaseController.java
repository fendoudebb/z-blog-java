package com.msj.blog.controller;


import com.msj.blog.entity.vo.Response;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * zbj: created on 2018/6/2 8:59.
 */
public class BaseController {
    @Resource
    protected HttpServletRequest request;

    protected <T> Response<T> getResponse(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    protected Response getResponse(String msg) {
        Response response = new Response();
        response.setMsg(msg);
        return response;
    }

}
