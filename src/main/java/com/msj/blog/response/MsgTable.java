package com.msj.blog.response;

import java.io.Serializable;

public class MsgTable implements Serializable{

    private static final long serialVersionUID = -7466519734119831888L;
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS = "请求成功";

    public static final String INTERNAL_ERROR = "内部错误";

//    public static final String NOT_LOGIN = "用户未登录";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGOUT_SUCCESS = "退出登录成功";

    public static final String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";

    public static final String REQUEST_METHOD_NOT_SUPPORT = "请求方式不正确";

    public static final String MISSING_PARAMETERS = "请求参数不完整";

    public static final String ACCESS_DENY = "权限不足";

    public static final String MISMATCH_TYPE = "请求参数类型不匹配";

    public static final String MISSING_REQUEST_PARAM = "缺少请求体参数";

    public static final String SAVE_ARTICLE_SUCCESS = "保存成功";

    public static final String SAVE_ARTICLE_FAILURE = "保存失败";

    public static final String EDIT_ARTICLE_SUCCESS = "修改成功";

    public static final String EDIT_ARTICLE_FAILURE = "修改失败";

    public static final String ARTICLE_NOT_EXIST = "文章不存在";

    public static final String MODIFY_AUDIT_STATUS_SUCCESS = "修改文章状态成功";


}
