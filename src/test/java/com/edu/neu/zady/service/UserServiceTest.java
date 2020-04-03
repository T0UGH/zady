package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends ZadyApplicationTests {

    @Resource
    UserService userService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void selectById(){
        Integer id = 1;
        User user = userService.selectById(id);
        Assertions.assertNotNull(user);
    }

    @Test
    void selectByEmail(){
        String email = "test@neu.com";
        User user = userService.selectByEmail(email);
        Assertions.assertNotNull(user);
    }

    @Test
    void register(){

        User user = new User();
        user.setName("testMan");

        user.setPassword("Neusoft123");
        //todo:DuplicateKeyException，邮箱是unique的这个要处理
        //todo:DataIntegrityViolationException，假如不该空为空，插入数据库会报错
        user.setEmail("neusoft1@neusoft.com");
        userService.register(user);
    }

    @Test
    void update(){
        User user = new User();
        user.setId(9);
        user.setName("testBoy");
        userService.update(user);
        User dbUser = userService.selectById(9);
        Assertions.assertEquals(user.getName(), dbUser.getName());
    }

    @AfterEach
    void tearDown() {
    }
}