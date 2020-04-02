package com.edu.neu.zady.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Override
    public User login(String email, String password) {
        User user = userService.selectByEmail(email);

        if(user == null || user.getId() == null || user.getPassword() == null){
            //todo:重新搞一搞异常这块的东西
            throw new RuntimeException("用户不存在");
        }

        if(!Encoder.string2Sha1(password).equals(user.getPassword())){
            throw new RuntimeException("密码错误");
        }



        Integer projectId = null;
        String role = null;
        if((projectId = user.getDefaultProjectId()) != null){
            Role roleObj = roleService.selectByPIdAndUId(projectId, user.getId());
            role = roleObj.getRole();
        }

        JWTCreator.Builder builder = JWT.create();
        //todo: 将list传入这个方法中.

        //token第一部分：userId
        builder.withClaim("userId", user.getId().toString());


        //token第二部分：projectId
        if(projectId != null){
            builder.withClaim("projectId", projectId.toString());
        }

        //token第三部分：role
        if(role != null){
            builder.withClaim("role", role);
        }





        String token =  builder.sign(Algorithm.none());
        user.setToken(token);

        return user;
    }

    @Override
    public User switchProject() {
        return null;
    }
}
