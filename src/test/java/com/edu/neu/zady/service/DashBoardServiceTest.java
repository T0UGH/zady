package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.DashBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class DashBoardServiceTest extends ZadyApplicationTests {

    private static final int DEFAULT_SLOT_NUM = 10;

    @Resource
    DashBoardService dashBoardService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert(){
        Integer sprintId = 1;
        int rv = dashBoardService.insert(sprintId);
        assertEquals(DEFAULT_SLOT_NUM, rv);
    }


    @Test
    void selectBySprint(){
        Integer sprintId = 1;
        DashBoard dashBoard = dashBoardService.selectBySprint(sprintId);
        assertNotNull(dashBoard);
    }

    @Test
    void selectByProject(){
        Integer projectId = 1;
        DashBoard dashBoard = dashBoardService.selectByProject(projectId);
        assertNotNull(dashBoard);
    }

    @Test
    void addBacklogNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addBacklogNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addFinishedBacklogNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addFinishedBacklogNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addStoryNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addStoryNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addInTimeStoryNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addInTimeStoryNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addOutTimeStoryNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addOutTimeStoryNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addBugNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addBugNum(sprintId);
        assertNotEquals(0, rv);
    }

    @Test
    void addSolvedBugNum(){
        Integer sprintId = 1;
        int rv = dashBoardService.addSolvedBugNum(sprintId);
        assertNotEquals(0, rv);
    }
}