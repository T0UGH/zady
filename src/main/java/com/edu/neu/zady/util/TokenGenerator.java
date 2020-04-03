package com.edu.neu.zady.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenGenerator {
    public static String generate(Integer userId, Integer projectId, String role){
        JWTCreator.Builder builder = JWT.create();

        //token第一部分：userId
        builder.withClaim("userId", userId.toString());


        //token第二部分：projectId
        if(projectId != null){
            builder.withClaim("projectId", projectId.toString());
        }

        //token第三部分：role
        if(role != null){
            builder.withClaim("role", role);
        }

        return  builder.sign(Algorithm.none());
    }
}
