package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

@RestController
public class TokenController {
    @Resource
    private TokenService tokenService;

    @PostMapping("/token")
    public User login(String email, String password){
        User user = tokenService.login(email, password);
        if(user == null){
            throw new DefaultException("登陆失败");
        }else {
            return user;
        }
    }

    @PutMapping("/token")
    @Auth(needProject = false)
    public String switchProject(Integer projectId){
        Integer currUserId = (Integer) RequestContextHolder
                .currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        String token = tokenService.switchProject(currUserId, projectId);
        if(token == null) {
            throw new DefaultException("切换失败，生成空密钥");
        }
        return token;
    }
}
