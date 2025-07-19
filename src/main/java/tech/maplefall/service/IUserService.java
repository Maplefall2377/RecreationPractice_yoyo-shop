package tech.maplefall.service;

import tech.maplefall.entity.User;

import java.util.List;

public interface IUserService {
    List<User> lists(String name);
    boolean checkExistsUserName(String username);//检测用户名是否存在
    boolean checkExistsPhone(String phone);//检测手机号是否存在
    boolean addUser(User user);//添加顾客
    User getUserById(Integer id);//根据ID查询顾客
    User getUserByName(String username);//根据用户名查询用户
    User getUserByPhone(String phone);//根据手机号查询用户
    boolean updateUser(User user);//修改用户信息
    boolean delUser(Integer id);//删除用户
}
