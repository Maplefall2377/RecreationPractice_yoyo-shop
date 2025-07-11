package tech.maplefall.entity;

import lombok.Data;

@Data
public class Type {
    private Integer id; // 类型ID
    private String name; // 类型名称
    private Integer status; // 状态（0：禁用，1：启用）
}
