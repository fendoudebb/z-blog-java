package com.msj.blog.config.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=2000,redisNamespace = "msj-blog-session")
public class RedisSessionConfig {
    /*@Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieMaxAge(2000);
        return defaultCookieSerializer;
    }*/
}  