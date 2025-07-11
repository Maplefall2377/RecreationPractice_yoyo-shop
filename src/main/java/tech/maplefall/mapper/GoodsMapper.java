package tech.maplefall.mapper;

import tech.maplefall.entity.Goods;
import tech.maplefall.entity.dto.GoodsDTO;

import java.util.List;

public interface GoodsMapper {
    List<GoodsDTO> lists(String name, Integer typeId, Integer types);//查询所有商品和对应的榜单top表
    int updateGoods(Goods goods);//更新商品信息
}
