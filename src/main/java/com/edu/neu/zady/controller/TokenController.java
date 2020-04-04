package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.util.DTOFactory;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TokenController {
    @Resource
    private TokenService tokenService;

    @PostMapping("/token")
    public User login(@RequestBody User user){
        User rUser = tokenService.login(user.getEmail(), user.getPassword());
        if(rUser == null){
            throw new DefaultException("登陆失败");
        }else {
            return rUser;
        }
    }

    @PutMapping("/token/{projectId}")
    @Auth(needProject = false)
    public DTO switchProject(@PathVariable Integer projectId){
        Integer currUserId = ParamHolder.getCurrentUserId();
        String token = tokenService.switchProject(currUserId, projectId);
        if(token == null) {
            throw new DefaultException("切换失败，生成空密钥");
        }
        return DTOFactory.okDTO(token);
    }
}
