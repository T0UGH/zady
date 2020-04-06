package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.Bug;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BugServiceTest extends ZadyApplicationTests {

    @Resource
    BugService bugService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert(){
        Bug bug = new Bug();
        bug.setStoryId(1);
        bug.setNote("代码错误");
        bug.setLevel(Bug.Level.C);
        bug.setName("代码错误");
        bug.setImageUrl("https://i.loli.net/2020/03/21/rtR3HPBNlMUjSAG.jpg");
        int rv = bugService.insert(bug);
        assertNotEquals(0, rv);
    }

    @Test
    void developNotConfirm(){
        Integer bugId = 1;
        Integer userId = 1;
        int rv = bugService.developNotConfirm(bugId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void developConfirm(){
        Integer bugId = 2;
        Integer userId = 1;
        int rv = bugService.developConfirm(bugId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void developFinish(){
        Integer bugId = 2;
        Integer userId = 1;
        int rv = bugService.developFinish(bugId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void testPass(){
        Integer bugId = 2;
        Integer userId = 1;
        int rv = bugService.testPass(bugId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void selectBySprintIdAndStatus(){
        Integer sprintId = 1;
        String statusStr = "已完成";
        List<Bug> bugList = bugService.selectBySprintIdAndStatus(sprintId, statusStr);
        assertNotNull(bugList);
        assertNotEquals(0, bugList.size());
    }
}