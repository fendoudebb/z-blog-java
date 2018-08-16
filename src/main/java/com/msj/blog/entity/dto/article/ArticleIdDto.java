package com.msj.blog.entity.dto.article;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * zbj: create on 2018/08/16 10:49
 */
@Data
public class ArticleIdDto {

    @NotNull(message = "articleId不能为空")
    @Min(value = 1,message = "articleId必须大于0")
    private Long articleId;

}
