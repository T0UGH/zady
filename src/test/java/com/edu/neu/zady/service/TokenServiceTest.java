package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;


class TokenServiceTest extends ZadyApplicationTests {

    @Resource
    TokenService tokenService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login(){
        String email = "neusoft@neusoft.com";
        String password = "Neusoft123";
        User user = tokenService.login(email, password);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getToken());
        System.out.println(user.getToken());
    }
}