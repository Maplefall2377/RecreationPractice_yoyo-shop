package tech.maplefall.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.Goods;
import tech.maplefall.entity.Top;
import tech.maplefall.entity.dto.GoodsDTO;
import tech.maplefall.service.IGoodsService;
import tech.maplefall.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping("/api/goods")
@RestController
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    //查询商品列表
    @GetMapping("/lists")
    private Result lists(Integer page, Integer pageSize, String name, Integer typeId, Integer type, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
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
    public Result updateStatus(@RequestBody Goods goods, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        boolean flag = goodsService.updateGoods(goods);
        return flag ? Result.success("修改成功") : Result.error("修改失败");
    }

    //添加商品
    @PostMapping("/saveGoods")
    public Result saveGoods(@RequestBody Goods goods, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        boolean flag = goodsService.saveGoods(goods);
        return flag ? Result.success("商品添加成功") : Result.error("商品添加失败");
    }

    //根据Id查询商品详情
    @GetMapping("/detail/{id}")
    public Result details(@PathVariable Integer id, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        Goods goods = goodsService.goodsDetails(id);
        return goods != null ? Result.success(goods) : Result.error("查询失败");
    }

    //删除商品
    @DeleteMapping("/delGoods/{id}")
    public Result delGoods(@PathVariable("id") Integer id, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        return goodsService.delGoods(id) ? Result.success("商品删除成功") : Result.error("商品删除失败");
    }

    //根据ID修改商品榜单类型
    @PutMapping("/updateTopType")
    public Result updateTopType(@RequestBody Top top, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        return goodsService.updateGoodsType(top.getId(), top.getType()) ? Result.success("商品榜单类型修改成功") : Result.error("商品榜单类型修改失败");
    }

}
