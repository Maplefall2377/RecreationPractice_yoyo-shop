package tech.maplefall.mapper;

import tech.maplefall.entity.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> getCartListByUserId(Integer userId);//根据购买人id查询购物车列表
    Integer getCartCountByUserId(Integer userId);//根据购买人id查询购物车商品总数量
    Integer getSingleCartCountByUserId(Integer userId, Integer cartId);//根据购买人id和购物车id查询购物车中该商品的数量
    Integer addCart(Integer goodsId, Integer userId);//根据商品Id和用户Id添加商品到购物车
    Integer delCart(Integer cartId, Integer userId);//根据购物车id和用户id删除购物车商品
    Integer updateCartNum(Integer num, Integer cartId);//根据购物车id更新商品数量, num为1时给商品的数量加1，num为-1时给商品的数量减1
    Integer moveCartToItem(Integer userId, Integer orderId); // 将购物车中的商品移到订单项中
    Integer clearCartByUserId(Integer userId); // 清空用户的购物车
}
