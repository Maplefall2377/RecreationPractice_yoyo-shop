package tech.maplefall.service;

import tech.maplefall.entity.Admin;

public interface IAdminService {

    // 登录
    Admin getAdminByUsername(String username);
}
