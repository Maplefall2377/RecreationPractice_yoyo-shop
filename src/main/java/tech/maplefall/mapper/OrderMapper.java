package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Param;
import tech.maplefall.entity.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> lists(@Param("orderNumber") String orderNumber, @Param("status") Integer status, @Param("paytype") Integer paytype); //查询订单列表
    int updateStatus(Order order); //更新订单状态
}
