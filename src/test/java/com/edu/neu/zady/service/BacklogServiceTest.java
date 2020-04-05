package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.Backlog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BacklogServiceTest extends ZadyApplicationTests {

    @Resource
    BacklogService backlogService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert(){
        Backlog backlog = new Backlog();
        backlog.setProjectId(1);
        backlog.setPriority(Backlog.Priority.C);
        backlog.setName("导出封装包");
        backlog.setNote("具备导出封装包的功能");
        backlog.setStatus(Backlog.Status.未开始);
        int rv = backlogService.insert(backlog);
        assertNotEquals(0, rv);
    }

    @Test
    void update(){
        Backlog backlog = new Backlog();
        backlog.setBacklogId(1);
        backlog.setNote("导出封装包");
        int rv = backlogService.update(backlog);
        assertNotEquals(0, rv);
    }

    @Test
    void selectByProjectId(){
        Integer projectId = 1;
        List<Backlog> backlogList =  backlogService.selectByProjectId(1);
        assertNotNull(backlogList);
        assertNotEquals(0, backlogList.size());
    }

    @Test
    void selectByProjectIdAndStatus(){
        Integer projectId = 1;
        String statusStr = "未开始";
        List<Backlog> backlogList =  backlogService.selectByProjectIdAndStatus(projectId, statusStr);
        assertNotNull(backlogList);
        assertNotEquals(0, backlogList.size());
    }

    @Test
    void addToCurrentSprint(){
        Integer backlogId = 1;
        int rv = backlogService.addToCurrentSprint(backlogId);
        assertNotEquals(0, rv);
    }

    @Test
    void selectBySprintId(){
        Integer sprintId = 1;
        List<Backlog> backlogList = backlogService.selectBySprintId(sprintId);
        assertNotNull(backlogList);
        assertNotEquals(0, backlogList.size());
    }

    @Test
    void removeFromCurrentSprint(){
        Integer backlogId = 1;
        int rv = backlogService.removeFromCurrentSprint(backlogId);
        assertNotEquals(0, rv);
    }

    @Test
    void delete(){
        Integer backlogId = 1;
        int rv = backlogService.delete(backlogId);
        assertNotEquals(0, rv);
    }
}