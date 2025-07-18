package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Param;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;

import java.util.List;

public interface IndexMapper {
    //首页显示部分接口
    List<Type> getEnableTypeLists();//显示已启用商品分类，常在小列表中展示商品分类
    List<Goods> getRecommendForIndex(@Param("type") Integer type);
    //查询推荐商品列表(参数type代表top表中的type，当它为1时为今日推荐，2为热销，3为新品)用于首页展示仅展示3个
    List<Goods> getGoodsByKeyword(String keyword, Integer offset, Integer size); //查询商品详情时使用，返回商品信息和类型信息
    Integer getCountByKeyword(String keyword); //查询商品详情时使用，返回商品数量
    List<Goods> getGoodsByTypeId(Integer typeId, Integer offset, Integer size); //根据商品类型ID查询商品列表
    Integer getCountByTypeId(Integer typeId); //根据商品类型ID查询商品数量
    Type getTypeById(Integer typeId); //根据类型ID查询类型信息
    List<Goods> getRecommendPage(Integer topTypeId, Integer offset, Integer size); // 根据榜单id从榜单中获取不同类型推荐商品列表，用于各榜单展示
    Integer getCountByTopTypeId(Integer topTypeId); // 根据榜单id获取榜单商品数量，用于各榜单展示
}
