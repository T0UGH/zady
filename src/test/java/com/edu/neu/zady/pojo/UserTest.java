package com.edu.neu.zady.pojo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        User user = new User();
        user.setId(1);
        assertEquals(1,user.getId());
    }

    @Test
    void getName() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void getAvatar() {
    }
}