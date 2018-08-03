package com.msj.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException{

    private Integer code;

    private String msg;

    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}