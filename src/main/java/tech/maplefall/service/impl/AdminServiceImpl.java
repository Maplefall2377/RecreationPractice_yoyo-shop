package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Admin;
import tech.maplefall.mapper.AdminMapper;
import tech.maplefall.service.IAdminService;


@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.getAdminByUsername(username);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public boolean updateAdminPassword(Admin admin) {
        return adminMapper.updateAdminPassword(admin) > 0? true : false;
    }
}

