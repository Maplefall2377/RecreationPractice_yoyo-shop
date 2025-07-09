package tech.maplefall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.maplefall.entity.Admin;
import tech.maplefall.service.IAdminService;
import tech.maplefall.util.Result;
import tech.maplefall.util.SafeUtils;

@Slf4j
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        if ("".equals(admin.getPassword())){
            return Result.error("密码不能为空");
        }
        if ("".equals(admin.getUsername())){
            return Result.error("管理员名不能为空");
        }
        Admin admin2 = adminService.getAdminByUsername(admin.getUsername());//根据页面发送的账号,获取对象
        if (admin2 == null){
            return  Result.error("账号或密码有误");
        }
        String pwd = SafeUtils.encode(admin.getPassword());//将页面用户的密码进行加密
        if (!admin2.getPassword().equals(pwd)){
            return Result.error("账号或密码有误");
        }

        return Result.success("登录成功");
    }
}

