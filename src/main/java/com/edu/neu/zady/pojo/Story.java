package com.edu.neu.zady.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Story {

    private Integer id;

    private Integer projectId;

    private Integer sprintId;

    private Integer backlogId;

    private Status status;

    private String name;

    private Integer expectedHours;

    private Integer currentHours;

    private Date createdDate;

    private Date finishedDate;

    private Integer developerId;

    private Integer testerId;

    private Boolean delete;

    public enum Status {
        待导入, 待完成, 完成中, 待测试, 测试中, 已完成
    }
}
