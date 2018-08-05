package com.msj.blog.config.mvc;

import com.msj.blog.config.executor.AppExecutor;
import com.msj.blog.interceptor.LogInterceptor;
import com.msj.blog.interceptor.RefererInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * zbj: create on 2018/06/15 09:52
 */
@Profile(value = {"dev"})
@Configuration
public class DevMvcConfig implements WebMvcConfigurer {

    @Resource
    private LogInterceptor logInterceptor;
    @Resource
    private RefererInterceptor refererInterceptor;
    @Resource
    private AppExecutor appExecutor;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor((AsyncTaskExecutor) appExecutor.getExecutor());
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8081");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns("/css/**", "/js/**", "/images/**");
        registry.addInterceptor(refererInterceptor).addPathPatterns("/css/**", "/js/**", "/images/**");
    }
}
