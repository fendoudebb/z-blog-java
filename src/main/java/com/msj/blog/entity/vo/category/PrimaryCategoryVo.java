package com.msj.blog.entity.vo.category;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * zbj: created on 2018/6/1 21:35.
 */
@Data
public class PrimaryCategoryVo {
    private Long id;
    private Integer sort;//模块排序
    private String name;//模块名字

    private List<SecondaryCategoryVo> secondaryCategories;

}
