package tech.maplefall.service;

import tech.maplefall.entity.User;

import java.util.List;

public interface IUserService {
    List<User> lists(String name);
}
