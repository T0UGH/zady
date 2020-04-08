package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.DashBoard;
import org.springframework.stereotype.Service;

@Service
public interface DashBoardService {

    Integer insert(Integer sprintId);

    DashBoard selectBySprint(Integer sprintId);

    DashBoard selectByProject(Integer projectId);

    Integer addBacklogNum(Integer sprintId);

    Integer addFinishedBacklogNum(Integer sprintId);

    Integer addStoryNum(Integer sprintId);

    Integer addInTimeStoryNum(Integer sprintId);

    Integer addOutTimeStoryNum(Integer sprintId);

    Integer addBugNum(Integer sprintId);

    Integer addSolvedBugNum(Integer sprintId);

}
