package com.jeffrey.service.impl;

import com.jeffrey.mapper.UserMapper;
import com.jeffrey.pojo.User;
import com.jeffrey.service.UserService;
import com.jeffrey.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username,md5String);

    }
}
