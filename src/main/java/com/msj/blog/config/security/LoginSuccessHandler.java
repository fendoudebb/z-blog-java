package com.msj.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * zbj: create on 2018/08/02 10:12
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Resource
    private ObjectMapper objectMapper; // Json转化工具

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        /*WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String sessionId = details.getSessionId();
        log.info("login--IP:" + details.getRemoteAddress() + ",sessionId: " + sessionId);
        SysUser user = (SysUser) authentication.getPrincipal();
        String name = authentication.getName();
        log.info("login--name:" + name + " principal:" + user.getUsername());*/
        try {
            log.info("login success: " + request.getParameter("username"));
            response.setContentType("application/json;charset=UTF-8");
            @Cleanup PrintWriter out = response.getWriter();
            Response<List<String>> res = new Response<>();
//            List<String> roles = user.getRoles().stream().map(SysRole::getName).collect(Collectors.toList());
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            res.setMsg(MsgTable.LOGIN_SUCCESS);
            res.setData(roles);
            out.append(objectMapper.writeValueAsString(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
