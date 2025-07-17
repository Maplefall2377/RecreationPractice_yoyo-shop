package tech.maplefall.service;

import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;

import java.util.List;

public interface IIndexService {
    List<Type> getEnableTypeLists();//显示已启用商品分类，常在小列表中展示商品分类
    List<Goods> getRecommend(Integer type); //查询推荐商品列表(参数top_type为1时为今日推荐，2为热销，3为新品)
}
