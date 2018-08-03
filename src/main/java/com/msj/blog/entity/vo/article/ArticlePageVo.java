package com.msj.blog.entity.vo.article;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * zbj: created on 2018/6/18 20:05.
 */
@Data
public class ArticlePageVo implements Serializable{
    private static final long serialVersionUID = 6836629346278301150L;

    private Long id;
    private String title;
    private String author;
    private String description;
    private boolean original;//是否原创
    private LocalDateTime updateTime;
    private String articleModule;
    private String articleModuleAlias;
}
