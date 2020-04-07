package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.Backlog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BacklogService {

    Backlog selectById(Integer backlogId);

    Boolean existById(Integer backlogId);

    List<Backlog> selectBySprintId(Integer sprintId);

    List<Backlog> selectBySprintIdAndStatus(Integer sprintId, String statusStr);

    List<Backlog> selectByProjectId(Integer projectId);

    List<Backlog> selectByProjectIdAndStatus(Integer projectId, String statusStr);

    Integer insert(Backlog backlog);

    Integer update(Backlog backlog);

    Integer addToCurrentSprint(Integer backlogId);

    Integer removeFromCurrentSprint(Integer backlogId);

    Integer delete(Integer backlogId);

    Integer finish(Integer backlogId);


}
