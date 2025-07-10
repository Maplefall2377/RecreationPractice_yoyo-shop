package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.maplefall.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> lists(String name);
    int checkExistsUserName(String username);//检测用户名是否存在
    int checkExistsPhone(String phone);//检测手机号是否存在
    int addUser(User user);//添加
    User getUserById(Integer id);//根据ID查询顾客
    int updateUser(User user);//修改用户信息
}
