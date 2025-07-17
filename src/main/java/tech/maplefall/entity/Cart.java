package tech.maplefall.entity;

import lombok.Data;

@Data
public class Cart {
    private Integer id;//主键id
    private String title;//商品的标题
    private String pic;//商品的图片
    private Double price;//商品的价格
    private Integer num;//商品的数量
    private Integer goods_id;//商品的id
    private Integer user_id;//购买人的id
}
