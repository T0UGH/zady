package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.pojo.DTO;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.util.DTOFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TokenController {
    @Resource
    private TokenService tokenService;

    @PostMapping("/token")
    public User login(String email, String password){
        User user = tokenService.login(email, password);
        if(user == null){
            throw new RuntimeException("登陆失败");
        }else {
            return user;
        }
    }

    @GetMapping("/token")
    @Auth(role = {Role.RoleEnum.developer, Role.RoleEnum.tester, Role.RoleEnum.owner, Role.RoleEnum.master})
    public DTO message(){
        return DTOFactory.okDTO();
    }
}
