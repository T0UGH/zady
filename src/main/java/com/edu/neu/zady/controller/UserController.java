package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册
     * */
    @PostMapping("/user")
    public String register(@RequestBody User user){
        if(userService.register(user) == 0){
            throw new DefaultException("注册失败");
        }else{
            return "注册成功";
        }
    }

    /**
     * 检测邮箱是否被使用了
     * */
    @GetMapping("/user/email/{email}")
    public Boolean existByEmail(@PathVariable String email){
        return userService.existByEmail(email);
    }

    /**
     * 读取某个用户的信息
     * */
    @Auth(needProject = false)
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId){
        User user =  userService.selectById(userId);
        if(user == null){
            throw new NotFoundException("用户不存在");
        }
        return user;
    }

    /**
     * 通过某个关键词搜索用户
     * */
    @Auth(needProject = false)
    @GetMapping("/users/{queryStr}")
    public List<User> getUsers(@PathVariable String queryStr){

        return userService.selectByQueryStr(queryStr);
    }


    /**
     * 用户修改自己的基本信息
     * */
    @Auth(needProject = false, sameUser = true)
    @PutMapping("/user")
    public String updateUser(@RequestBody User user){

        if(!ParamHolder.sameUser(user.getUserId())){
            throw new NoAuthException("不能修改其他用户的个人信息");
        }

        if(userService.update(user) == 0){
            throw new DefaultException("更新失败");
        }
        return "更新成功";
    }
}
