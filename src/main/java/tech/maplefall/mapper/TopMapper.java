package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Param;
import tech.maplefall.entity.Top;

public interface TopMapper {
    int saveTop(Top top);//添加榜单信息
    int updateTopType(@Param("id") Integer id, @Param("type") Integer type);//根据id修改榜单类型
}
