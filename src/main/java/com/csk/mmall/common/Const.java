package com.csk.mmall.common;

public class Const {

    //用户身份
    public static final String CURRENT_USER = "currentUser";
    //校验类型
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    //用户角色
    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
