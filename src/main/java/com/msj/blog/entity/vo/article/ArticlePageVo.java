package com.msj.blog.entity.vo.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msj.blog.util.LocalDateTime2StringDeserializer;
import com.msj.blog.util.LocalDateTime2StringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * zbj: created on 2018/6/18 20:05.
 */
@Data
public class ArticlePageVo {

    private Long id;
    private String title;
    private String author;
    private String description;
    private boolean original;//是否原创
    @JsonSerialize(using = LocalDateTime2StringSerializer.class)
    @JsonDeserialize(using = LocalDateTime2StringDeserializer.class)
    private LocalDateTime createTime;
    private String category;
}
