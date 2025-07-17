package tech.maplefall.service;

import tech.maplefall.entity.Order;

import java.util.List;

public interface IOrderService {
    List<Order> lists(String orderNumber, Integer status, Integer paytype); //查询订单列表
    boolean updateStatus(Order order); //更新订单状态
}
