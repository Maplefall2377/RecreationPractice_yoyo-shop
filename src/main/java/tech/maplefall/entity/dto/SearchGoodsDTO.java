package tech.maplefall.entity.dto;

import lombok.Data;
import tech.maplefall.entity.Goods;

import java.util.List;

@Data
public class SearchGoodsDTO {
    private Integer count; // 搜索结果数量
    private List<Goods> goods; // 搜索结果商品列表
}
