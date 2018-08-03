package com.msj.blog.entity.vo;

import java.io.Serializable;

public class MsgTable implements Serializable{

    private static final long serialVersionUID = -7466519734119831888L;
    public static Integer SUCCESS_CODE = 0;
    public static String SUCCESS = "请求成功";

    public static String NOT_LOGIN = "用户未登录";

    public static String LOGIN_SUCCESS = "登录成功";

    public static String LOGOUT_SUCCESS = "退出登录成功";

    public static String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";

    public static String MISSING_PARAMETERS = "请求参数不完整";

    public static String MISMATCH_TYPE = "请求参数类型不匹配";

    public static String MISSING_REQUEST_PARAM = "缺少请求体参数";

    public static String INTERNAL_ERROR = "内部错误";
}
