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
    public List<Goods> getRecommendForIndex(Integer type) {
        return indexMapper.getRecommendForIndex(type);
    }

    @Override
    public List<Goods> getGoodsByKeyword(String keyword, Integer offset, Integer size) {
        return indexMapper.getGoodsByKeyword(keyword, offset, size);
    }

    @Override
    public Integer getCountByKeyword(String keyword) {
        return indexMapper.getCountByKeyword(keyword);
    }

    @Override
    public Type getTypeById(Integer typeId) {
        return indexMapper.getTypeById(typeId);
    }

    @Override
    public List<Goods> getRecommendPage(Integer topTypeId, Integer offset, Integer size) {
        return indexMapper.getRecommendPage(topTypeId, offset, size);
    }

    @Override
    public Integer getCountByTopTypeId(Integer topTypeId) {
        return indexMapper.getCountByTopTypeId(topTypeId);
    }

    @Override
    public List<Goods> getGoodsByTypeId(Integer typeId, Integer offset, Integer size) {
        return indexMapper.getGoodsByTypeId(typeId, offset, size);
    }

    @Override
    public Integer getCountByTypeId(Integer typeId) {
        return indexMapper.getCountByTypeId(typeId);
    }
}
