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
}
