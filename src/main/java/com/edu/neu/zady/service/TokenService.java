package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService{

    User login(String email, String password);

    User switchProject();
}

