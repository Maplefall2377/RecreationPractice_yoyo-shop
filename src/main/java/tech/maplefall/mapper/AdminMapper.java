package tech.maplefall.mapper;

import tech.maplefall.entity.Admin;

public interface AdminMapper {
    // 根据管理员名获取管理员对象
    Admin getAdminByUsername(String username);
    Admin getAdminById(Integer id);//根据管理员id获取管理员对象
    int updateAdminPassword(Admin admin);//修改管理员密码
}
