package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.Sprint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SprintService {

    Sprint selectById(Integer sprintId);

    Boolean existById(Integer sprintId);

    List<Sprint> selectByProjectId(Integer projectId);

    Sprint selectCurrentByProjectId(Integer projectId);

    Integer start(Sprint sprint);

    Integer update(Sprint sprint);

    Integer end(Integer sprintId);
}
