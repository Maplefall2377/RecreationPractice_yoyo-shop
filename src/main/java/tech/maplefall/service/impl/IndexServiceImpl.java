package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;
import tech.maplefall.mapper.IndexMapper;
import tech.maplefall.service.IIndexService;

import java.util.List;

@Service
public class IndexServiceImpl implements IIndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<Type> getEnableTypeLists() {
        return indexMapper.getEnableTypeLists();
    }

    @Override
    public List<Goods> getRecommend(Integer type) {
        return indexMapper.getRecommend(type);
    }
}
