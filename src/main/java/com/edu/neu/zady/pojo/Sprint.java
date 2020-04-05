package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Sprint {

    @TableId
    private Integer sprintId;

    private Integer projectId;

    private Integer roundId;

    private String name;

    private String note;

    private Status status;

    private Date startDate;

    private Date expectedEndDate;

    private Date actualEndDate;

    public enum Status {
        进行中,已完成
    }
}
