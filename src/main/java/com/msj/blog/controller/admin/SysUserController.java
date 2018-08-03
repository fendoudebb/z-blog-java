package com.msj.blog.controller.admin;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class SysUserController {

    @Secured("ROLE_ADMIN")
    @RequestMapping("/add")
    @ResponseBody
    public Object save() {
        return "addUser-OK";
    }

    @Secured("ROLE_USER")
    @GetMapping("/all")
    public String look() {
        return "main";
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(@RequestParam(value = "aaa") String aaa) {
        return aaa;
    }

    @Async
    public String get() {
        for (int i = 1; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "********" + i);
        }
        return "ok";
    }
}
