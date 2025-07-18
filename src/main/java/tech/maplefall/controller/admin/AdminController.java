package tech.maplefall.controller.admin;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maplefall.entity.Admin;
import tech.maplefall.entity.dto.AdminDTO;
import tech.maplefall.service.IAdminService;
import tech.maplefall.util.RedisUtils;
import tech.maplefall.util.Result;
import tech.maplefall.util.SafeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisUtils redisUtils;

    //管理员登录
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

        //生成用户令牌，作为用户身份，id拼接username，然后进行加密，加密后的串作为令牌
        String token = SafeUtils.encode(admin2.getId() + admin2.getUsername());
        //将令牌存入redis中,key是token，value是user对应的map
        Map<String, Object> adminMap = objectMapper.convertValue(admin2, new TypeReference<Map<String, Object>>() {});
        redisUtils.hmset(token, adminMap, 7200); // 设置过期时间为2小时

        return Result.success("登录成功",token);
    }

    //管理员登出
    @GetMapping("/logout/{id}")
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("token-admin");
        redisUtils.del(token); // 删除令牌
        return Result.success("登出成功");
    }

    //管理员密码修改
    @PutMapping("/updatepwd")
    public Result updateAdminPwd(@RequestBody AdminDTO adminDTO, HttpServletRequest request) {
        // 未登录管理员无法进行以下操作
        String tokenAdmin = request.getHeader("token-admin");
        if (tokenAdmin == null || "".equals(tokenAdmin)) {
            return Result.error("NOTLOGIN");
        }
        String oldPwd = SafeUtils.encode(adminDTO.getPassword());//将页面用户传来的的旧密码进行加密
        String newPwd = SafeUtils.encode(adminDTO.getNewPwd());//将页面用户传来的的新密码进行加密

        //从Redis中根据token获取管理员id
        Integer adminId = (Integer) redisUtils.hmget(adminDTO.getToken()).get("id");

        //根据管理员id从数据库中获取管理员对象
        Admin admin = adminService.getAdminById(adminId);

        //判断旧密码是否正确
        if (!admin.getPassword().equals(oldPwd)) {
            return Result.error("旧密码错误，请重新输入");
        }
        //更新管理员密码
        admin.setPassword(newPwd);

        //删除Redis中的管理员信息token
        redisUtils.del(adminDTO.getToken());

        return adminService.updateAdminPassword(admin)?Result.success("密码修改成功"): Result.error("密码修改失败");
    }
}

