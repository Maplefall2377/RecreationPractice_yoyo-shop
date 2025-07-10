package tech.maplefall.mapper;

import tech.maplefall.entity.Admin;

public interface AdminMapper {
    // 根据管理员名获取管理员对象
    Admin getAdminByUsername(String username);
}
