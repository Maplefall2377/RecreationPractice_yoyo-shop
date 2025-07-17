package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.User;
import tech.maplefall.mapper.UserMapper;
import tech.maplefall.service.IUserService;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> lists(String name) {
        return userMapper.lists(name);
    }

    @Override
    public boolean checkExistsUserName(String username) {
        return userMapper.checkExistsUserName(username)>0? true : false;
    }

    @Override
    public boolean checkExistsPhone(String phone) {
        return userMapper.checkExistsPhone(phone) > 0 ? true : false;
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user) > 0 ? true : false;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user) > 0 ? true : false;
    }

    @Override
    public boolean delUser(Integer id) {
        return userMapper.delUser(id) > 0 ? true : false;
    }

}
