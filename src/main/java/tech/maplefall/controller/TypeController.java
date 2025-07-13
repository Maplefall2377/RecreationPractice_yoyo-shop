package tech.maplefall.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.Type;
import tech.maplefall.service.ITypeService;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/type")
@RestController
public class TypeController {

    @Autowired
    private ITypeService typeService;

    //查询分类列表，显示已启用的分类
    @GetMapping("/lists")
    private Result lists() {
        List<Type> types = typeService.lists();
        return types.size()> 0 ? Result.success(types): Result.error("没有数据");
    }

    //查询所有分类列表（无论是否启用）
    @GetMapping("/listall")
    private Result listall() {
        List<Type> types = typeService.listall();
        return types.size() > 0 ? Result.success(types) : Result.error("没有数据");
    }

    //根据分类ID查询分类详情
    @GetMapping("/detail/{id}")
    private Result getTypeById(@PathVariable("id") Integer id) {
        Type type = typeService.getTypeById(id);
        return type != null ? Result.success(type) : Result.error("查询失败");
    }

    //更新分类信息，已包含更新状态
    @PutMapping("/updateType")
    public Result updateType(@RequestBody Type type) {
        boolean flag = typeService.updateType(type);
        return flag ? Result.success("修改成功") : Result.error("修改失败");
    }

    //添加分类
    @PostMapping("/saveType")
    public Result addType(@RequestBody Type type) {
        boolean flag = typeService.addType(type);
        return flag ? Result.success("添加成功") : Result.error("添加失败");
    }

    //删除分类，根据ID删除分类
    @DeleteMapping("/delType/{id}")
    public Result delType(@PathVariable("id") Integer id) {
        boolean flag = typeService.delType(id);
        return flag ? Result.success("删除成功") : Result.error("删除失败");
    }
}
