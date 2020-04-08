package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DashBoard {

    @TableId
    private Integer dashBoardId;

    private Integer sprintId;

    private Integer projectId;

    private Integer slotId;

    private Integer backlogNum;

    private Integer finishedBacklogNum;

    private Integer storyNum;

    private Integer finishedStoryNum;

    private Integer bugNum;

    private Integer solvedBugNum;

    private Integer inTimeStoryNum;

    private Integer outTimeStoryNum;
}
