package tech.maplefall.service;


import tech.maplefall.entity.Goods;
import tech.maplefall.entity.dto.GoodsDTO;

import java.util.List;

public interface IGoodsService {
    List<GoodsDTO> lists(String name, Integer typeId, Integer type);//查询所有商品和对应的榜单top表
    boolean updateGoods(Goods goods);//更新商品信息
    boolean saveGoods(Goods goods);//添加商品
    Goods goodsDetails(Integer id);//根据ID查询商品详情
    boolean delGoods(Integer id);//删除商品
    boolean updateGoodsType(Integer id, Integer type);//根据ID修改商品类型
    List<Goods> rankGoodsBySales();//根据销量排行商品
}
