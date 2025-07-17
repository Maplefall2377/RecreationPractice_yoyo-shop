package tech.maplefall.mapper;

import tech.maplefall.entity.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> getCartListByUserId(Integer userId);//根据购买人id查询购物车列表
    Integer getCartCountByUserId(Integer userId);//根据购买人id查询购物车商品数量
    Integer addCart(Integer goodsId, Integer userId);//根据商品Id和用户Id添加商品到购物车
}
