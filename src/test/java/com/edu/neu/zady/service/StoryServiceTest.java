package com.edu.neu.zady.service;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StoryServiceTest extends ZadyApplicationTests {

    @Resource
    StoryService storyService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert(){
        Story story = new Story();
        story.setBacklogId(1);
        story.setExpectedHours(8);
        story.setName("我是一个小小小，我想要一个小功能导出个小包");
        int rv = storyService.insert(story);
        assertNotEquals(0, rv);
    }

    @Test
    void update(){
        Story story = new Story();
        story.setStoryId(1);
        story.setTesterId(1);
        story.setName("我是一个管理员，我想要一个小功能导出包");
        int rv = storyService.update(story);
        assertNotEquals(0 ,rv);
    }

    @Test
    void importStory(){
        Integer storyId = 1;
        int rv = storyService.importStory(storyId);
        assertNotEquals(0, rv);
    }

    @Test
    void returnStory(){
        Integer storyId = 1;
        int rv = storyService.returnStory(storyId);
        assertNotEquals(0, rv);
    }

    @Test
    void developReceive(){
        Integer storyId = 1;
        Integer userId = 1;
        int rv = storyService.developerReceive(storyId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void developReturn(){
        Integer storyId = 1;
        Integer userId = 1;
        int rv = storyService.developReturn(storyId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void developFinish(){
        Integer storyId = 1;
        Integer userId = 1;
        Integer useHours = 5;
        int rv = storyService.developFinish(storyId, userId, useHours);
        assertNotEquals(0, rv);
    }

    @Test
    void testReceive(){
        Integer storyId = 1;
        Integer userId = 1;
        int rv = storyService.testReceive(storyId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void testReturn(){
        Integer storyId = 1;
        Integer userId = 1;
        int rv = storyService.testReturn(storyId, userId);
        assertNotEquals(0, rv);
    }

    @Test
    void testNotPass(){
        Integer storyId = 1;
        Integer userId = 1;
        Integer useHours = 1;
        int rv =storyService.testNotPass(storyId, userId, useHours);
        assertNotEquals(0, rv);
    }

    @Test
    void testPass(){
        Integer storyId = 1;
        Integer userId = 1;
        Integer useHours = 1;
        int rv =storyService.testPass(storyId, userId, useHours);
        assertNotEquals(0, rv);
    }
}