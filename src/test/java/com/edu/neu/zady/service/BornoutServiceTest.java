package com.edu.neu.zady.service;

import com.baomidou.mybatisplus.annotation.TableId;
import com.edu.neu.zady.ZadyApplicationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BornoutServiceTest extends ZadyApplicationTests {

    @Resource
    BornoutService bornoutService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addBornout(){
        Integer sprintId = 1;
        Date createDate = new Date();
        bornoutService.addBornout(sprintId, createDate);
        bornoutService.addBornout(sprintId, createDate);
    }
}