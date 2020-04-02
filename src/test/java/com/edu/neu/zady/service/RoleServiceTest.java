package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest extends ZadyApplicationTests {

    @Autowired
    RoleService roleService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void inviteUser(){
        Integer projectId = 1;
        Integer userId = 9;
        int affectRow = roleService.inviteUser(projectId, userId);
        Assertions.assertEquals(1, affectRow);
    }
}