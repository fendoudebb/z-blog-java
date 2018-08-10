package com.msj.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msj.blog.entity.vo.MsgTable;
import com.msj.blog.entity.vo.Response;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * zbj: create on 2018/08/02 11:03
 */
@Slf4j
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        log.info("登出成功");

        @Cleanup PrintWriter writer = response.getWriter();
        Response<String> res = new Response<>();
        res.setMsg(MsgTable.LOGOUT_SUCCESS);
        writer.append(objectMapper.writeValueAsString(res));
    }
}
