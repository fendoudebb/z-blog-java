package com.msj.blog.controller.rest.wechat;

import com.msj.blog.entity.vo.wechat.WechatCallback;
import com.msj.blog.property.WechatProperty;
import com.msj.blog.util.WechatSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/06/12 15:35
 */
@Slf4j
@Controller
@RequestMapping("/maisiji")
public class WechatController {

    @Resource
    private WechatProperty wechatProperty;

    @GetMapping("/wechat")
    @ResponseBody
    public String validConnect(@RequestParam String signature, @RequestParam String timestamp,
                               @RequestParam String nonce, @RequestParam String echostr) {
        log.info(wechatProperty.toString());
        boolean result = WechatSignUtil.checkSignature(signature, wechatProperty.getToken(), timestamp, nonce);
        return result ? echostr : null;
    }

    @PostMapping("/wechat")
    @ResponseBody
    public void callback(@RequestBody WechatCallback callback) {
        log.info(callback.toString());
    }
}
