package tech.maplefall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.dto.GoodsDTO;
import tech.maplefall.service.IGoodsService;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/goods")
@RestController
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    //查询商品列表
    @GetMapping("/lists")
    private Result lists(Integer page, Integer pageSize, String name, Integer typeId, Integer type) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize);
        List<GoodsDTO> goodsDTOList = goodsService.lists(name, typeId, type);
        PageInfo<GoodsDTO> pageInfo = new PageInfo<>(goodsDTOList);

        return Result.success(pageInfo);
    }

    //更新商品信息
    @PutMapping("/updateGoods")
    public Result updateStatus(@RequestBody Goods goods){
        boolean flag = goodsService.updateGoods(goods);
        return flag ? Result.success("修改成功") : Result.error("修改失败");
    }
}
