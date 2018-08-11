package com.msj.blog.entity.vo.category;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * zbj: created on 2018/6/1 21:35.
 */
@Data
public class PrimaryCategoryVo implements Serializable{
    private static final long serialVersionUID = -4170283708297346795L;
    private Long id;
    private Integer sort;//模块排序
    private String name;//模块名字
    private String alias;//别名

    private List<SecondaryCategoryVo> secondaryCategories;

}
