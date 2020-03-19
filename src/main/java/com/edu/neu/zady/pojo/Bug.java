package com.edu.neu.zady.pojo;

import lombok.Data;

@Data
public class Bug {

    private Integer id;

    private Integer projectId;

    private Integer sprintId;

    private Integer backlogId;

    private Integer storyId;

    private String name;

    private String note;

    private String imageUrl;

    private Level level;

    private Status status;

    private Integer testerId;

    private Integer developerId;

    public enum Level{
        A, B, C, D
    }

    public enum Status{
        待确认, 待修改, 待复核, 已完成
    }
}
