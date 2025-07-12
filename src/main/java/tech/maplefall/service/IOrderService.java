package tech.maplefall.service;

import org.springframework.stereotype.Service;
import tech.maplefall.entity.Order;

import java.util.List;

@Service
public interface IOrderService {
    List<Order> lists(String orderNumber, Integer status, Integer paytype); //查询订单列表
}
