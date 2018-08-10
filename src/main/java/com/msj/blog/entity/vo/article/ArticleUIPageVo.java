package com.msj.blog.entity.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * zbj: created on 2018/6/18 20:05.
 */
@Data
public class ArticleUIPageVo implements Serializable{
    private static final long serialVersionUID = 6836629346278301150L;

    private Long id;
    private String title;
    private String author;
    private String description;
    private boolean original;//是否原创
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime updateTime;
    private String articleCategory;
    private String articleCategoryAlias;
}
