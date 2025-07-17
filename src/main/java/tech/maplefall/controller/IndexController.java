package tech.maplefall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;
import tech.maplefall.service.IIndexService;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/index")
@RestController
public class IndexController {

    @Autowired
    private IIndexService indexService;

    // 查询已启用的商品分类列表
    @GetMapping("/kinds")
    private Result kinds() {
        List<Type> types = indexService.getEnableTypeLists();
        return types.size() > 0 ? Result.success(types): Result.error("没有数据");
    }

    // 查询今日推荐
    @GetMapping("/todayChoice")
    private Result todayChoice() {
        List<Goods> todaychoice = indexService.getRecommend(1);
        return todaychoice.size() > 0 ? Result.success(todaychoice) : Result.error("没有数据");
    }

    // 查询热销和新品商品
    @GetMapping("/recommend")
    private Result recommend(@RequestParam Integer type) {
        List<Goods> recommend = indexService.getRecommend(type);
        return recommend.size() > 0 ? Result.success(recommend) : Result.error("没有数据");
    }
}
