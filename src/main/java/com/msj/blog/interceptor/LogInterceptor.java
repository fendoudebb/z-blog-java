package com.msj.blog.interceptor;

import com.msj.blog.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zbj: create on 2018/06/15 13:57
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("ip address: [{}], request url: [{}]", IpUtil.getIpAddress(), request.getRequestURI());
        return true;
    }
}
