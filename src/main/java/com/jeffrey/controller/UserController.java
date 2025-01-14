package com.jeffrey.controller;

import com.jeffrey.pojo.Result;
import com.jeffrey.pojo.User;
import com.jeffrey.service.UserService;
import com.jeffrey.utils.JwtUtil;
import com.jeffrey.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u =  userService.findByUserName(username);
        if (u == null) {
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }
        //注册
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User loginuser =  userService.findByUserName(username);
        if (loginuser == null) {
            return Result.error("用户名错误");
        }
        if (Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginuser.getId());
            claims.put("username",loginuser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

}
