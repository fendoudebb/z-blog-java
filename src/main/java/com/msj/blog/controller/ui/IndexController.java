package com.msj.blog.controller.ui;

import com.msj.blog.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        log.info(IpUtil.getIpAddress() + ": get error path");
        return "error/404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
