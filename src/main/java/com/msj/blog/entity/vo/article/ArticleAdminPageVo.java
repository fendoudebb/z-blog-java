package com.msj.blog.entity.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * zbj: create on 2018/08/06 10:36
 */
@Data
public class ArticleAdminPageVo implements Serializable{
    private static final long serialVersionUID = -6378756504694367864L;

    private Long id;
    private String title;
    private String author;
    private String keywords;
    private String description;
    private boolean original;//是否原创
    private String originalLink;//原创链接
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime updateTime;
    private String category;
    private String categoryAlias;

    private String auditStatus;

}
