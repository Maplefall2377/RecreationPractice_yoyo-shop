package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Type;
import tech.maplefall.mapper.TypeMapper;
import tech.maplefall.service.ITypeService;

import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> lists() {
        return typeMapper.lists();
    }

    @Override
    public List<Type> listall() {
        return typeMapper.listall();
    }

    @Override
    public Type getTypeById(Integer id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public int getTypeCountById(Integer id) {
        return typeMapper.getTypeCountById(id);
    }
    @Override
    public boolean updateType(Type type) {
        return typeMapper.updateType(type) > 0 ? true : false;
    }

    @Override
    public boolean addType(Type type) {
        return typeMapper.addType(type) > 0 ? true : false;
    }

    @Override
    public boolean delType(Integer id) {
        return typeMapper.delType(id) > 0 ? true : false;
    }
}
