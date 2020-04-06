package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Bug {

    @TableId
    private Integer bugId;

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

    @TableLogic
    private Boolean flag;

    public enum Level{
        A, B, C, D
    }

    public enum Status{
        待确认, 待修改, 待复核, 已完成
    }
}
