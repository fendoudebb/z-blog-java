package com.msj.blog.controller.admin.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@PreAuthorize(value = "hasRole('ADMIN')")
public class SysUserController {

    @RequestMapping("/add")
    @ResponseBody
    public Object save() {
        return "addUser-OK";
    }

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
