package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Param;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;

import java.util.List;

public interface IndexMapper {
    //首页显示部分接口
    List<Type> getEnableTypeLists();//显示已启用商品分类，常在小列表中展示商品分类
    List<Goods> getRecommend(@Param("type") Integer type); //查询推荐商品列表(参数type代表top表中的type，当它为1时为今日推荐，2为热销，3为新品)
}
