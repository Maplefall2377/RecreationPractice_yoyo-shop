package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import tech.maplefall.entity.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> lists(@Param("orderNumber") String orderNumber, @Param("status") Integer status, @Param("paytype") Integer paytype); //查询订单列表
    int updateStatus(Order order); //更新订单状态
    List<Order> getOrdersByUserId(@Param("userId") Integer userId); // 根据用户ID获取订单列表

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int submitOrder(Order order); // 提交订单，返回订单id号
}
