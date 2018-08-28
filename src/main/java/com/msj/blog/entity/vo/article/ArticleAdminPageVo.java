package com.msj.blog.entity.vo.article;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * zbj: create on 2018/08/06 10:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleAdminPageVo extends ArticlePageVo{

    private String auditStatus;

}
