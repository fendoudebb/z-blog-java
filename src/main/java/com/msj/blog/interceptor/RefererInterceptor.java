package com.msj.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zbj: create on 2018/06/15 13:57
 */
@Slf4j
public class RefererInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String referrer = request.getHeader("referer");
        //防盗链
        if (referrer != null && referrer.contains("maisiji.cn")) {
            return true;
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
    }
}
