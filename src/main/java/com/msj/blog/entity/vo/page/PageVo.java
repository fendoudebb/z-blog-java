package com.msj.blog.entity.vo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVo<T> {

    private Integer number;

    private Integer numberOfElements;

    private Integer size;

    private Long totalElements;

    private Integer totalPages;

    private List<T> content;

}
