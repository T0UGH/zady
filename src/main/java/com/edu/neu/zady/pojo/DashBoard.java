package com.edu.neu.zady.pojo;

import lombok.Data;

@Data
public class DashBoard {
    private Integer sprintId;
    private Integer projectId;
    private Integer backlogNum;
    private Integer finishedBacklogNum;
    private Integer storyNum;
    private Integer finishedStoryNum;
    private Integer bugNum;
    private Integer solvedBugNum;
    private Integer inTimeStoryNum;
    private Integer outTimeStoryNum;
}
