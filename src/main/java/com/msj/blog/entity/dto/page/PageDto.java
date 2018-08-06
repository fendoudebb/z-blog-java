package com.msj.blog.entity.dto.page;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * zbj: create on 2018/08/06 11:10
 */
@Data
public class PageDto {

    @NotNull(message = "page不能为空")
    @Min(value = 0, message = "page不能小于0")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Min(value = 1, message = "size不能小于等于0")
    private Integer size;

}
