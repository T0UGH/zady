package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user")
    public String register(@RequestBody User user){
        if(userService.register(user) == 0){
            throw new DefaultException("注册失败");
        }else{
            return "注册成功";
        }
    }

    @GetMapping("/user/email/{email}")
    public Boolean existByEmail(@PathVariable String email){
        return userService.existByEmail(email);
    }

    @Auth(needProject = false)
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId){
        User user =  userService.selectById(userId);
        if(user == null){
            throw new NotFoundException("用户不存在");
        }
        return user;
    }

    @Auth(needProject = false)
    @GetMapping("/users")
    public List<User> getUsers(String queryStr){

        return userService.selectByQueryStr(queryStr);
    }


    //只有用户自己可以修改自己的基本信息
    @Auth(needProject = false, sameUser = true)
    @PutMapping("/user")
    public String updateUser(User user){

        if(userService.update(user) == 0){
            throw new DefaultException("更新失败");
        }
        return "更新成功";
    }
}
