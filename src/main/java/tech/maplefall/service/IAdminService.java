package tech.maplefall.service;

import tech.maplefall.entity.Admin;

public interface IAdminService {

    // 登录
    Admin getAdminByUsername(String username);
    Admin getAdminById(Integer id); // 根据管理员id获取管理员对象
    boolean updateAdminPassword(Admin admin); // 修改管理员密码
}
