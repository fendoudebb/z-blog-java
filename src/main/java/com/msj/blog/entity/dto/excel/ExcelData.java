package com.msj.blog.entity.dto.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * zbj: create on 2018/08/08 15:33
 */
@Data
public class ExcelData implements Serializable{
    private static final long serialVersionUID = -1490790383057167695L;

    // 表头
    private List<String> titles;

    // 数据
    private List<List<Object>> rows;

    // 页签名称
    private String name;

}
