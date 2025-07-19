package tech.maplefall.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.Order;
import tech.maplefall.service.ICartService;
import tech.maplefall.service.IOrderService;
import tech.maplefall.util.RedisUtils;
import tech.maplefall.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/listspage")
    public Result list(Integer page, Integer pageSize, String orderNumber, Integer status, Integer paytype, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        if (page == null){
            page= 1;
        }
        if (pageSize== null){
            pageSize=10;
        }

        PageHelper.startPage(page,pageSize);
        List<Order> orderList = orderService.lists(orderNumber,status,paytype);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);

        return Result.success(pageInfo);
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        return orderService.updateStatus(order) ? Result.success("修改成功") : Result.error("修改失败");
    }

    @PostMapping("/list")
    public Result getOrdersByUserId(@RequestParam String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 获取用户订单列表
        List<Order> orderList = orderService.getOrdersByUserId(userId);
        return Result.success("请求成功", orderList);
    }

    // 提交订单
    @PostMapping("/submit")
    public Result submitOrder(@RequestParam String token, @RequestParam("payType") Integer paytype, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());

        // 提交订单
        int orderId = orderService.submitOrder(userId, paytype, name, phone, address);
        int mcti = cartService.moveCartToItem(userId, orderId); // 将购物车中的商品移动到订单项中
        int ccbu = cartService.clearCartByUserId(userId); // 清空用户的购物车
        if (orderId > 0 && mcti > 0 && ccbu > 0) {
            return Result.success("订单提交成功", orderId);
        } else {
            return Result.error("订单提交失败");
        }
    }
}
