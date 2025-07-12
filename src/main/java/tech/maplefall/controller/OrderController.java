package tech.maplefall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.Order;
import tech.maplefall.service.IOrderService;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/listspage")
    public Result list(Integer page, Integer pageSize, String orderNumber, Integer status, Integer paytype){
        if (page == null){
            page= 1;
        }
        if (pageSize== null){
            pageSize=10;
        }

        PageHelper.startPage(page,pageSize);
        List<Order> orderList = orderService.lists(orderNumber,status,paytype);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);

        return Result.success(pageInfo);
    }
}
