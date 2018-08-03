package com.msj.blog.config.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;

/**
 * zbj: created on 2018/6/11 21:47.
 */
@Configuration
public class XmlConfig {

    /*@Bean
    public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
        return new Jaxb2RootElementHttpMessageConverter();
    }*/

    @Bean
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        return new MarshallingHttpMessageConverter();
    }

}
