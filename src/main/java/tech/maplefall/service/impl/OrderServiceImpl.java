package tech.maplefall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maplefall.entity.Order;
import tech.maplefall.mapper.OrderMapper;
import tech.maplefall.service.IOrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> lists(String orderNumber, Integer status, Integer paytype) {
        return orderMapper.lists(orderNumber, status, paytype);
    }

    @Override
    public boolean updateStatus(Order order) {
        return orderMapper.updateStatus(order) > 0 ? true : false;
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderMapper.getOrdersByUserId(userId);
    }

    @Override
    public int submitOrder(Integer userId, Integer payType, String name, String phone, String address) {
        Order order = new Order();
        order.setUserId(userId);
        order.setPaytype(payType);
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);

        orderMapper.submitOrder(order);

        return order.getId(); // 返回生成的主键
    }
}
