package tech.maplefall.mapper;

import org.apache.ibatis.annotations.Mapper;
import tech.maplefall.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> lists(String name);
}
