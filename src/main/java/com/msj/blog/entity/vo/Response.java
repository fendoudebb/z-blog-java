package com.msj.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable{
    private static final long serialVersionUID = 2131439430753816731L;
    private Integer code = MsgTable.SUCCESS_CODE;
    private String msg = MsgTable.SUCCESS;
    private T data;

    public Response<T> fail() {
        this.code = -1;
        return this;
    }

}
