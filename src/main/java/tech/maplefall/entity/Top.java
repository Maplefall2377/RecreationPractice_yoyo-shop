package tech.maplefall.entity;

import lombok.Data;

@Data
public class Top {
    private Integer id; // 榜单ID
    private Integer type; // 榜单类型（1：今日推荐，2：热销，3：新品榜）
    private Integer goodsId; // 商品ID

}
