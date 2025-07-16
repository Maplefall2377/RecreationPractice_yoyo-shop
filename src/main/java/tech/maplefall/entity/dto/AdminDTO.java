package tech.maplefall.entity.dto;

import lombok.Data;
import tech.maplefall.entity.Admin;

@Data
public class AdminDTO extends Admin {
    private String newPwd;//新密码
    private String token;//登录令牌
}
