package tech.maplefall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.User;
import tech.maplefall.service.IUserService;
import tech.maplefall.util.Result;
import tech.maplefall.util.SafeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    //查询用户是否存在
    @GetMapping("/lists")
    private Result lists(Integer page, Integer pageSize, String name, HttpServletRequest request) {
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
        List<User> userList = userService.lists(name);
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        return Result.success(pageInfo);
    }

    //添加
    @PostMapping("/adduser")
    public Result addUser(@RequestBody User user, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        //判断账号是否存在
        boolean flag1 = userService.checkExistsUserName(user.getUsername());
        if (flag1){
            return Result.error("用户名已存在");
        }
        // 判断手机号是否存在
        boolean flag2 = userService.checkExistsPhone(user.getPhone());
        if (flag2){
            return Result.error("手机号已被注册");
        }
        // 注册
        String pwd = SafeUtils.encode(user.getPassword());//加密
        user.setPassword(pwd);
        boolean flag3 = userService.addUser(user);
        return flag3?Result.success("注册成功"):Result.error("注册失败");
    }

    //根据ID查询顾客信息
    @GetMapping("/getuserbyid/{id}")
    public Result getUserById(@PathVariable Integer id, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        User user = userService.getUserById(id);
        return user != null ? Result.success(user) : Result.error("查询失败");
    }

    //修改顾客：修改单个或全部
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
//        //密码加密（老师没加）
//        String pwd = SafeUtils.encode(user.getPassword());//加密密码
//        user.setPassword(pwd);
        boolean flag = userService.updateUser(user);
        return flag ? Result.success("修改成功") : Result.error("修改失败");
    }

    //账号禁用启用
    @PutMapping("/updateUserStatus")
    public Result updateUserStatus(@RequestBody User user, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        boolean flag = userService.updateUser(user);
        return flag ? Result.success("修改成功") : Result.error("修改失败");
    }

    //删除顾客
    @DeleteMapping("/delUser")
    public Result delUser(Integer id, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        boolean flag = userService.delUser(id);
        return flag ? Result.success("删除成功") : Result.error("删除失败");
    }
}
