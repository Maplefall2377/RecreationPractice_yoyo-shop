package tech.maplefall.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    //查询类型列表
    @GetMapping("/lists")
    private Result lists() {
        List<Type> types = typeService.lists();
        return types.size()> 0 ? Result.success(types): Result.error("没有数据");
    }
}
