package tech.maplefall.service;

import tech.maplefall.entity.User;

import java.util.List;

public interface IUserService {
    List<User> lists(String name);
    boolean checkExistsUserName(String username);//检测用户名是否存在
    boolean checkExistsPhone(String phone);//检测手机号是否存在
    boolean addUser(User user);
}
