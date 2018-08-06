package com.msj.blog.entity.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * zbj: create on 2018/08/01 19:42
 */
@Data
public class ArticleDto {

    @NotBlank(message = "标题不为空")
    private String title;
    private String author;
    @NotBlank(message = "关键字不为空")
    private String keywords;
    @NotBlank(message = "描述不为空")
    private String description;
    private boolean original;//是否原创
    private String originalLink;//原文链接
    @NotBlank(message = "内容不为空")
    private String content;
    @NotNull(message = "分类不能为空")
    private Long articleModuleId;

}
