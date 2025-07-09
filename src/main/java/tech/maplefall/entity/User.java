package tech.maplefall.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;//用户ID
    private String username;//用户名
    private String password;//用户登录密码
    private String name;//用户真实姓名
    private String phone;//用户手机号
    private String address;//用户地址
    private Integer status;//用户状态
}
