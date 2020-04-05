package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Backlog {

    @TableId
    private Integer backlogId;

    private Integer projectId;

    private String name;

    private String note;

    private Priority priority;

    private Status status;

    private Integer sprintId;

    @TableLogic
    private Boolean flag;

    public enum Status {
        未开始, 进行中, 已完成
    }

    public enum Priority {
        A, B, C, D, E
    }
}
