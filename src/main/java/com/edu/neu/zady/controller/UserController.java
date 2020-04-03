package com.edu.neu.zady.controller;

import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.DTOFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user")
    public String register(@RequestBody User user){
        if(userService.register(user) == 0){
            throw new RuntimeException("注册失败");
        }else{
            return "注册成功";
        }
    }
}
