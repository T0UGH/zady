package com.edu.neu.zady.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    UserService userService;

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
        if(user.getDefaultProjectId() != null){
            //todo:从role表中，用projectId和userId取出对应的role
        }

        JWTCreator.Builder builder = JWT.create();
        builder.withAudience(user.getId().toString());

        if(projectId != null){
            builder.withAudience(projectId.toString());
        }

        String role = null;
        if(projectId != null){
            //查询
        }

        if(role != null){
            builder.withAudience(role);
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
