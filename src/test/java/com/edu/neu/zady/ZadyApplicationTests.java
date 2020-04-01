package com.edu.neu.zady;

import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ZadyApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(tokenService);
    }

}
