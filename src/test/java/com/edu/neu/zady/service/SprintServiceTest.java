package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.pojo.Sprint;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SprintServiceTest extends ZadyApplicationTests {

    @Resource
    SprintService sprintService;

    @Test
    void start() throws ParseException {
        Sprint sprint = new Sprint();
        sprint.setProjectId(1);
        sprint.setName("sp1");
        sprint.setNote("一轮迭代");
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse("2020-06-01");
        sprint.setExpectedEndDate(date);
        int rv = sprintService.start(sprint);
        assertNotEquals(0, rv);
    }

    @Test
    void update() throws ParseException {
        Sprint sprint = new Sprint();
        sprint.setSprintId(1);
        sprint.setName("zady:sp1");
        sprint.setNote("一轮迭代");
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse("2020-07-01");
        sprint.setExpectedEndDate(date);
        int rv = sprintService.update(sprint);
        assertNotEquals(0, rv);
    }

    @Test
    void selectByProjectId(){
        Integer projectId = 1;
        List<Sprint> sprintList =  sprintService.selectByProjectId(projectId);
        assertNotNull(sprintList);
    }

    @Test
    void selectCurrentByProjectId(){
        Integer projectId = 1;
        Sprint sprint =  sprintService.selectCurrentByProjectId(projectId);
        assertNotNull(sprint);
    }

    @Test
    void end(){
        Integer sprintId = 1;
        int rv = sprintService.end(sprintId);
        assertNotEquals(0, rv);
    }

}