package com.msj.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msj.blog.response.MsgTable;
import com.msj.blog.response.Response;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * zbj: create on 2018/08/02 11:41
 */
@Slf4j
@Component
public class PermissionDenyHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        log.info("permission deny");

        response.setContentType("application/json;charset=UTF-8");
        @Cleanup PrintWriter writer = response.getWriter();
        Response<String> res = new Response<>();
        res.setMsg(MsgTable.ACCESS_DENY);
        writer.write(objectMapper.writeValueAsString(res.fail()));
    }
}
