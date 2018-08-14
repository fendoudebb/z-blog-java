package com.msj.blog.entity.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * zbj: created on 2018/8/11 22:54.
 */
@Data
public class AuditStatusDto {

    @NotNull(message = "文章id不能为空")
    private Long articleId;

    @NotBlank(message = "审核状态不能为空")
    private String auditStatus;

}
