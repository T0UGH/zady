package com.edu.neu.zady.pojo;

import lombok.Data;

@Data
public class Backlog {

    private Integer id;

    private Integer projectId;

    private String name;

    private String note;

    private Priority priority;

    private Status status;

    private Integer sprintId;

    private Boolean flag;

    public enum Status {
        未开始, 进行中, 已完成
    }

    public enum Priority {
        A, B, C, D, E
    }
}
