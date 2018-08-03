package com.msj.blog.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * zbj: create on 2018/06/12 15:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatProperty {
    private String appId;
    private String appSecret;
    private String token;
}
