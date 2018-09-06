package com.msj.blog.entity.vo.page;

import lombok.Data;

import java.util.List;

/**
 * zbj: create on 2018/09/06 11:40
 */
@Data
public class SliceVo<T> {

    private Integer number;

    private Integer size;

    private Integer numberOfElements;

    private List<T> content;

    private Boolean hasContent;

    private Boolean hasNext;

    private Boolean hasPrevious;

    private Boolean isFirst;

    private Boolean isLast;

}
