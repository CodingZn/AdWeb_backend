package com.example.adweb_backend.validation;

public class UserValidation {
    public static final String USERNAME_REGEX = "^[A-Za-z][A-Za-z0-9_\\-]{5,15}$";
    public static final String USERNAME_MSG = "用户名长度6-16位，包含字母、数字、下划线和_，只能以字母开头";

    public static final String NICKNAME_REGEX = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{2,16}$";
    public static final String NICKNAME_MSG = "昵称长度2-16位，包含字母、数字、汉字、下划线和_";

    public static final String PHONE_REGEX = "^(13|14|15|18|17)[0-9]{9}$";
    public static final String PHONE_MSG = "请输入11位国内手机号";

    public static final String EMAIL_REGEX = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$";
    public static final String EMAIL_MSG = "请输入正确邮箱地址";

    public static final String PASSWORD_REGEX = "^[\\w\\W]{6,16}$";
    public static final String PASSWORD_MSG = "密码长度需在6-16位之间";

    public static final String NOTNULL_MSG = "必填项不能为空！";

}
