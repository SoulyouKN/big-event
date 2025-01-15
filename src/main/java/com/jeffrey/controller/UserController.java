package com.jeffrey.controller;

import ch.qos.logback.core.util.StringUtil;
import com.jeffrey.pojo.Result;
import com.jeffrey.pojo.User;
import com.jeffrey.service.UserService;
import com.jeffrey.utils.JwtUtil;
import com.jeffrey.utils.Md5Util;
import com.jeffrey.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        //检查输入
        if(!StringUtils.hasLength(old_pwd) && StringUtils.hasLength(new_pwd) && StringUtils.hasLength(re_pwd)) {
            return Result.error("缺少必要输入");
        }
        //检查原密码
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = map.get("username").toString();
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("原密码输入错误");
        }
        //检查两次密码是否一致
        if(!new_pwd.equals(re_pwd)) {
            return Result.error("两次密码不一致");
        }
        userService.updatePwd(new_pwd);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@Validated @RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        Object username = map.get("username");

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName((String) username);
        return Result.success(user);
    }

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
