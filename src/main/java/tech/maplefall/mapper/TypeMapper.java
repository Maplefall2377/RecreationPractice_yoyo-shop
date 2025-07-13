package tech.maplefall.mapper;

import tech.maplefall.entity.Type;

import java.util.List;

public interface TypeMapper {
    List<Type> lists();//显示已启用商品分类，常在小列表中展示商品分类
    List<Type> listall(); //查询所有商品分类（无论是否启用）
    Type getTypeById(Integer id); //根据分类ID查询类型详情，常用于修改
    int getTypeCountById(Integer id);// 根据分类ID查询分类下商品数量，常用于删除前判断是否有商品使用该分类
    int updateType(Type type); //更新分类信息，已包含更新状态
    int addType(Type type); //添加分类
    int delType(Integer id); //删除分类，根据ID删除分类
}