package com.msj.blog.entity.vo.article;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * zbj: created on 2018/6/1 21:33.
 */
@Data
public class ArticleCategoryVo implements Serializable {
    private static final long serialVersionUID = -3275664519622321291L;
    private Integer id;
    private Integer sort;
    private String name;
    private String alias;

    private String articleModule;

}
