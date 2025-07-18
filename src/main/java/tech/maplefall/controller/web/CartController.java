package tech.maplefall.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.Cart;
import tech.maplefall.service.ICartService;
import tech.maplefall.util.RedisUtils;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/cart")
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private RedisUtils redisUtils;

    // 获取购物车列表
    @PostMapping("/getCartList")
    public Result getCartList(@RequestParam String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 获取购物车列表
        List<Cart> cart = cartService.getCartListByUserId(userId);
        return Result.success("获取购物车列表成功", cart);
    }

    // 获取用户的购物车商品数量
    @PostMapping("/getCartNum")
    public Result getCartNum(@RequestParam String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 获取购物车商品数量
        Integer cartCount = cartService.getCartCountByUserId(userId);
        return Result.success("获取购物车商品数量成功", cartCount);
    }

    // 添加商品到用户的购物车
    @PostMapping("/addCart")
    public Result addCart(@RequestParam("id") Integer goodsId, @RequestParam("token") String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 添加商品到购物车
        Integer result = cartService.addCart(Integer.valueOf(goodsId), userId);
        return result > 0 ? Result.success("添加商品到购物车成功") : Result.error("添加商品到购物车失败");
    }

    // 从用户的购物车中删除商品
    @PostMapping("/delCart")
    public Result delCart(@RequestParam("cartId") Integer cartId, @RequestParam("token") String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 从购物车中删除商品
        Integer result = cartService.delCart(cartId, userId);
        return result > 0 ? Result.success("从购物车中删除商品成功") : Result.error("从购物车中删除商品失败");
    }

    // 更改购物车中某商品的数量
    @PostMapping("/changeNum")
    public Result changeNum(@RequestParam("num") Integer num, @RequestParam("cartId") Integer cartId, @RequestParam("token") String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 更改购物车中商品的数量
        Integer result = cartService.updateCartNum(num, cartId);

        // 检查购物车中该商品还是否存在
        Integer singleCartCount = cartService.getSingleCartCountByUserId(userId, cartId);
        if (singleCartCount == 0) {
            cartService.delCart(cartId, userId);// 如果数量为0，则删除该商品
        }

        return result > 0 ? Result.success("更改购物车中商品数量成功") : Result.error("更改购物车中商品数量失败");
    }
}
