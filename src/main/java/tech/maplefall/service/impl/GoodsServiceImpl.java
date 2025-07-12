package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Top;
import tech.maplefall.entity.dto.GoodsDTO;
import tech.maplefall.mapper.GoodsMapper;
import tech.maplefall.mapper.TopMapper;
import tech.maplefall.service.IGoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private TopMapper topMapper;

    @Override
    public List<GoodsDTO> lists(String name, Integer typeId, Integer type) {
        return goodsMapper.lists(name, typeId, type);
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return goodsMapper.updateGoods(goods) > 0 ? true : false;
    }

    @Transactional
    @Override
    public boolean saveGoods(Goods goods) {
        // 在商品添加完成后，返回商品ID，之后再在top表插入ID和榜单ID（默认为3，为新品）
        goodsMapper.saveGoods(goods);
        Top top = new Top();
        top.setType(3);// 默认为新品榜单
        top.setGoodsId(goods.getId());
        int rows = topMapper.saveTop(top);
        return rows > 0 ? true : false;
    }

    @Override
    public Goods goodsDetails(Integer id) {
        return goodsMapper.getGoodsById(id);
    }
}
