package tech.maplefall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.User;
import tech.maplefall.service.IUserService;
import tech.maplefall.util.Result;

import java.util.List;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/lists")
    private Result lists(Integer page, Integer pageSize, String name) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        PageHelper.startPage(page, pageSize);
        List<User> userList = userService.lists(name);
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        return Result.success(pageInfo);
    }
}
