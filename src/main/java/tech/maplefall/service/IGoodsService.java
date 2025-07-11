package tech.maplefall.service;


import tech.maplefall.entity.dto.GoodsDTO;

import java.util.List;

public interface IGoodsService {
    List<GoodsDTO> lists(String name, Integer typeId, Integer type);//查询所有商品和对应的榜单top表
}
