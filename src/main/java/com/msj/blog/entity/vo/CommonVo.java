package com.msj.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonVo implements Serializable{

    private static final long serialVersionUID = 3867617399087039512L;
    private Integer code;
    private String msg;

}
