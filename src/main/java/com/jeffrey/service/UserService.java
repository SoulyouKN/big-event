package com.jeffrey.service;

import com.jeffrey.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}
