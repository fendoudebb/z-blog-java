package com.msj.blog.entity.dto.article;

import com.msj.blog.entity.FieldLength;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * zbj: create on 2018/08/01 19:42
 */
@Data
public class ArticleDto {

    @NotBlank(message = "标题不能为空")
    @Length(max = FieldLength.ARTICLE_TITLE, message = "标题长度不能超出" + FieldLength.ARTICLE_TITLE + "个字符")
    private String title;
    @Length(max = FieldLength.ARTICLE_AUTHOR, message = "作者长度不能超出" + FieldLength.ARTICLE_AUTHOR + "个字符")
    private String author;
    @NotBlank(message = "关键字不能为空")
    @Length(max = FieldLength.ARTICLE_KEYWORDS, message = "关键词长度不能超出" + FieldLength.ARTICLE_KEYWORDS + "个字符")
    private String keywords;
    @NotBlank(message = "描述不能为空")
    @Length(max = FieldLength.ARTICLE_DESCRIPTION, message = "描述长度不能超出" + FieldLength.ARTICLE_DESCRIPTION + "个字符")
    private String description;
    private boolean original = true;//是否原创
    @Length(max = FieldLength.URL_LINK, message = "链接长度不能超出" + FieldLength.URL_LINK + "个字符")
    private String originalLink;//原文链接
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotBlank(message = "分类不能为空")
    @Length(max = FieldLength.NORMAL, message = "分类长度不能超出" + FieldLength.NORMAL + "个字符")
    private String category;

    @NotBlank(message = "属性不能为空")
    private String articleProperty;

}
