package com.msj.blog.controller.admin.user;

import org.springframework.security.access.annotation.Secured;
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
}
