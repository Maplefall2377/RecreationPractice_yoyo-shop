package tech.maplefall.mapper;

import tech.maplefall.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> lists(String name);
    int checkExistsUserName(String username);//检测用户名是否存在
    int checkExistsPhone(String phone);//检测手机号是否存在
    int addUser(User user);//添加
    User getUserById(Integer id);//根据ID查询顾客
    User getUserByName(String username);//根据用户名查询用户
    User getUserByPhone(String phone);//根据手机号查询用户
    int updateUser(User user);//修改用户信息
    int delUser(Integer id);//删除用户
}
