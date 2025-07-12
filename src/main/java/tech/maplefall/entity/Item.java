package tech.maplefall.entity;

import lombok.Data;

@Data
public class Item {
    private Integer id; // 商品ID
    private String title; // 商品名称
    private String pic; // 商品描述
    private Double price; // 商品价格
    private Integer amount; // 商品库存
    private Integer goodsId; // 商品ID
    private Integer orderId; // 订单ID
}
