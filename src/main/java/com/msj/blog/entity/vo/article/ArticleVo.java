package com.msj.blog.entity.vo.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msj.blog.util.LocalDateTime2StringDeserializer;
import com.msj.blog.util.LocalDateTime2StringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String author;
    private String keywords;
    private String description;
    private boolean original;//是否原创
    private String originalLink;//原文链接
    private String content;
    @JsonSerialize(using = LocalDateTime2StringSerializer.class)
    @JsonDeserialize(using = LocalDateTime2StringDeserializer.class)
    private LocalDateTime createTime;
    @JsonSerialize(using = LocalDateTime2StringSerializer.class)
    @JsonDeserialize(using = LocalDateTime2StringDeserializer.class)
    private LocalDateTime updateTime;
    private String category;

}
