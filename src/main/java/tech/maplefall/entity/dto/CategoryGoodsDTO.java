package tech.maplefall.entity.dto;

import lombok.Data;
import tech.maplefall.entity.Type;

@Data
public class CategoryGoodsDTO extends SearchGoodsDTO{
    private Type type; // 商品分类信息
}
