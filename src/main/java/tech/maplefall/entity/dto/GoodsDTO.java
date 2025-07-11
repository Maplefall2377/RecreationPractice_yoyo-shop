package tech.maplefall.entity.dto;

import lombok.Data;
import tech.maplefall.entity.Goods;

@Data
public class GoodsDTO extends Goods {
    private Integer tid;// top表ID
    private Integer types;// top表中商品类型
}
