package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.maplefall.entity.Admin;

@Mapper
public interface AdminMapper {
    // 根据管理员名获取管理员对象
    Admin getAdminByUsername(String username);
}
