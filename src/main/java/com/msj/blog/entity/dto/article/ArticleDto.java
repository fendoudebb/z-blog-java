package com.msj.blog.entity.dto.article;

import lombok.Data;

/**
 * zbj: create on 2018/08/01 19:42
 */
@Data
public class ArticleDto {

    private Long id;
    private String title;
    private String author;
    private String keywords;
    private String description;
    private boolean original;//是否原创
    private String originalLink;//原文链接
    private String content;
    private Long articleModuleId;

}
