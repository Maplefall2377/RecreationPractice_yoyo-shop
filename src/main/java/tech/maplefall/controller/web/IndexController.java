package tech.maplefall.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Type;
import tech.maplefall.entity.dto.CategoryGoodsDTO;
import tech.maplefall.entity.dto.SearchGoodsDTO;
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
        List<Goods> todaychoice = indexService.getRecommendForIndex(1);
        return todaychoice.size() > 0 ? Result.success(todaychoice) : Result.error("没有数据");
    }

    // 查询热销和新品商品
    @GetMapping("/recommend")
    private Result recommend(@RequestParam Integer type) {
        List<Goods> recommend = indexService.getRecommendForIndex(type);
        return recommend.size() > 0 ? Result.success(recommend) : Result.error("没有数据");
    }

    // 根据关键字搜索商品
    @GetMapping("/search")
    private Result search(@RequestParam("kw") String keyword, @RequestParam("page") Integer page,
                          @RequestParam("size") Integer size) {
        Integer offset = (page - 1) * size;
        List<Goods> goods = indexService.getGoodsByKeyword(keyword, offset, size);
        Integer count = indexService.getCountByKeyword(keyword);
        SearchGoodsDTO searchGoodsDTO = new SearchGoodsDTO();
        searchGoodsDTO.setCount(count);
        searchGoodsDTO.setGoods(goods);
        return count > 0 ? Result.success(searchGoodsDTO) : Result.error("没有数据");
    }

    // 根据商品分类查询商品列表
    @GetMapping("/kindsGoods")
    private Result kindsGoods(@RequestParam("type") Integer typeId, @RequestParam("page") Integer page,
                              @RequestParam("size") Integer size) {
        Integer offset = (page - 1) * size;
        List<Goods> goods = indexService.getGoodsByTypeId(typeId, offset, size);
        Integer count = indexService.getCountByTypeId(typeId);
        Type type = indexService.getTypeById(typeId);
        CategoryGoodsDTO categoryGoodsDTO = new CategoryGoodsDTO();
        categoryGoodsDTO.setCount(count);
        categoryGoodsDTO.setGoods(goods);
        categoryGoodsDTO.setType(type);
        return count > 0 ? Result.success(categoryGoodsDTO) : Result.error("没有数据");
    }

    // 根据榜单分类查询推荐商品列表
    @GetMapping("/recommendPage")
    private Result recommendPage(@RequestParam("type") Integer topTypeId,
                                 @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Integer offset = (page - 1) * size;
        List<Goods> goods = indexService.getRecommendPage(topTypeId, offset, size);
        Integer count = indexService.getCountByTopTypeId(topTypeId);
        SearchGoodsDTO searchGoodsDTO = new SearchGoodsDTO();
        searchGoodsDTO.setCount(count);
        searchGoodsDTO.setGoods(goods);
        return count > 0 ? Result.success(searchGoodsDTO) : Result.error("没有数据");
    }
}
