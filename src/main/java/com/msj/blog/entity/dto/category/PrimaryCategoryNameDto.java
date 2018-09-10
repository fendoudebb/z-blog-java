package com.msj.blog.entity.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * zbj: create on 2018/09/10 11:02
 */
@Data
public class PrimaryCategoryNameDto {

    @NotBlank(message = "一级分类菜单名称不能为空")
    private String primaryCategoryName;

}
