package com.edu.neu.zady.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.neu.zady.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService{
    public String getToken();
}

