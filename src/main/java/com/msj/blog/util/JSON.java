package com.msj.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * zbj: created on 2018/8/29 20:18.
 */
@Slf4j
public class JSON {

    public static String parse(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("parse json string exception:[{}]", e.getMessage());
        }
        return jsonString;
    }

}
