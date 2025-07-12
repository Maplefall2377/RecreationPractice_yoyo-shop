package tech.maplefall.entity;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Integer id; //订单ID
    private String orderNumber; // 订单号
    private Double total; // 总价
    private Integer amount; // 购买数量
    private Integer status; // 订单状态（1:待支付 2:已支付 3:已发货 4:已完成 0:已取消）
    private Integer paytype; // 支付方式（如：支付宝、微信、银行卡等）
    private String name; // 收货人姓名
    private String phone; // 收货人电话
    private String address; // 收货地址
    private String systime;// 下单时间
    private Integer userId; // 用户ID

    // 一对多
    private List<Item> itemList;//一个订单可以有多个商品项
}
