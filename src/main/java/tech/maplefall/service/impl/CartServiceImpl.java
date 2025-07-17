package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Cart;
import tech.maplefall.mapper.CartMapper;
import tech.maplefall.service.ICartService;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> getCartListByUserId(Integer userId) {
        return cartMapper.getCartListByUserId(userId);
    }

    @Override
    public Integer getCartCountByUserId(Integer userId) {
        return cartMapper.getCartCountByUserId(userId);
    }

    @Override
    public Integer addCart(Integer goodsId, Integer userId) {
        return cartMapper.addCart(goodsId, userId);
    }
}
