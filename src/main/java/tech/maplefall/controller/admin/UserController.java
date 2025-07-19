package tech.maplefall.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.User;
import tech.maplefall.service.IUserService;
import tech.maplefall.util.RedisUtils;
import tech.maplefall.util.Result;
import tech.maplefall.util.SafeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisUtils redisUtils;

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String phone,
                           @RequestParam String address) {
        // 检查用户名是否已存在
        if (userService.checkExistsUserName(username)) {
            return Result.error("用户名已存在");
        }
        // 检查手机号是否已存在
        if (userService.checkExistsPhone(phone)) {
            return Result.error("手机号已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(SafeUtils.encode(password)); //密码加密
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        if (userService.addUser(user)) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败，请稍后再试");
        }
    }

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password){
        if ("".equals(password)){
            return Result.error("密码不能为空");
        }
        if ("".equals(username)){
            return Result.error("用户名不能为空");
        }
        User user2 = userService.getUserByName(username);//根据页面发送的账号,获取对象
        if (user2 == null){
            return  Result.error("账号或密码有误");
        }
        String pwd = SafeUtils.encode(password);//将页面用户的密码进行加密
        if (!user2.getPassword().equals(pwd)){
            return Result.error("账号或密码有误");
        }

        //生成用户令牌，作为用户身份，id拼接username，然后进行加密，加密后的串作为令牌
        String token = SafeUtils.encode(user2.getId() + user2.getUsername());
        //将令牌存入redis中,key是token，value是user对应的map
        Map<String, Object> userMap = objectMapper.convertValue(user2, new TypeReference<Map<String, Object>>() {});
        redisUtils.hmset(token, userMap, 7200); // 设置过期时间为2小时

        return Result.success("登录成功",token);
    }

    //检测用户token是否有效
    @PostMapping("/checkLogin")
    public Result checkLogin(@RequestParam String token) {
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        //从redis中获取用户信息
        Map<Object, Object> userMap = redisUtils.hmget(token);
        if (userMap == null || userMap.isEmpty()) {
            return Result.error("用户未登录");
        }

        //将map转换为User对象
        User user = objectMapper.convertValue(userMap, User.class);
        if (user == null) {
            return Result.error("用户未登录");
        }

        // 检查用户是否存在
        String username = user.getUsername();
        User existingUser = userService.getUserByName(username);
        if (existingUser == null) {
            return Result.error("用户不存在，登陆失败");
        }

        return Result.success("用户已登录", user);
    }

    //用户登出
    @PostMapping("/logout")
    public Result logout(@RequestParam String token) {
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        //从redis中删除用户信息
        redisUtils.del(token);
        return Result.success("登出成功");
    }


    // 查询单个用户信息，可用于前端个人中心
    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());
        User user = userService.getUserById(userId);
        return Result.success("请求成功", user);
    }

    //更改用户信息，用于前端用户自己更改
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestParam String name, @RequestParam String phone, @RequestParam String address, @RequestParam String token) {
        //根据token获取用户信息
        if (token == null || "".equals(token)) {
            return Result.error("用户未登录");
        }

        Object userIdObj = redisUtils.hget(token, "id");
        if (userIdObj == null) {
            return Result.error("用户未登录或登录已过期");
        }
        Integer userId = Integer.valueOf(userIdObj.toString());
        User user = userService.getUserById(userId);

        if (user == null) {
            return Result.error("用户不存在");
        }

        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        boolean isUpdated = userService.updateUser(user);
        if (isUpdated) {
            // 更新redis中的用户信息
            Map<String, Object> userMap = objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {});
            redisUtils.hmset(token, userMap, 7200); // 设置过期时间为2小时
            return Result.success("修改成功");
        }
        else
        {
            return Result.error();
        }
    }

    //查询所有用户
    @GetMapping("/lists")
    private Result lists(Integer page, Integer pageSize, String name, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            // 可以加一个!redisUtils.hasKey(tokenAdmin)作为新的或条件来检查token是否在redis中，拓宽判断条件，后续的所有用到管理员token判断都需要更改
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
        //密码加密（老师没加）
        String pwd = SafeUtils.encode(user.getPassword());//加密密码
        user.setPassword(pwd);
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
