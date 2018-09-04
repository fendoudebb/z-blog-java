package com.msj.blog.entity.vo.category;

import lombok.Data;

import java.io.Serializable;

/**
 * zbj: created on 2018/6/1 21:33.
 */
@Data
public class SecondaryCategoryVo {
    private Integer id;
    private Integer sort;
    private String name;

    private String primaryCategory;

}
