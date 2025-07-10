package tech.maplefall.entity;

import lombok.Data;

@Data
public class Goods {
    private Integer id;// 商品ID
    private String name;// 商品名称
    private Double price;// 商品价格
    private String intro;// 商品简介
    private String cover;// 商品封面
    private String image1;// 商品图片1
    private String image2;// 商品图片2
    private Integer stock;// 商品库存
    private Integer typeId;// 商品类型ID
    private Integer status;// 商品状态（0：禁用，1：启用）
}
