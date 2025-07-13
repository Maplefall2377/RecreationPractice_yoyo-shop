package tech.maplefall.service;

import tech.maplefall.entity.Type;

import java.util.List;

public interface ITypeService {
    List<Type> lists();//显示已启用商品类型，常在小列表中展示商品类型
    List<Type> listall(); // 查询所有商品类型（无论是否启用）
    Type getTypeById(Integer id); // 根据类型ID查询类型详情，常用于修改
    int getTypeCountById(Integer id); // 根据类型ID查询类型下商品数量，常用于删除前判断是否有商品使用该类型
    boolean updateType(Type type); // 更新类型信息，已包含更新状态
    boolean addType(Type type); // 添加类型
    boolean delType(Integer id); // 删除类型，根据ID删除类型
}
