package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.dto.GoodsDTO;
import tech.maplefall.mapper.GoodsMapper;
import tech.maplefall.service.IGoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsDTO> lists(String name, Integer typeId, Integer type) {
        return goodsMapper.lists(name, typeId, type);
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return goodsMapper.updateGoods(goods) > 0 ? true : false;
    }
}
