package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest extends ZadyApplicationTests {

    @Resource
    ProjectService projectService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert(){
        Project project = new Project();
        project.setName("测试项目0404");
        project.setNote("测试项目0404的简介");
        project.setGithubUrl("https://github.com/T0UGH/zady");
        int rv = projectService.insert(project, 9);
        assertEquals(1, rv);
    }

    @Test
    void update(){
        Project project = new Project();
        project.setProjectId(4);
        project.setName("测试项目0405");
        project.setGithubUrl("https://github.com/T0UGH/watersoup");
        projectService.update(project);
    }

    @Test
    void selectById(){
        assertNotNull(projectService.selectById(1));
    }

    @Test
    void existById(){
        assertTrue(projectService.existById(1));
    }

    @Test
    void existById2(){
        assertFalse(projectService.existById(2));
    }
}