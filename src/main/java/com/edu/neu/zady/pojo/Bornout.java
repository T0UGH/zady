package com.edu.neu.zady.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Bornout {

    private Integer bornoutId;

    private Integer sprintId;

    private Date createDate;

    private Integer finishedStoryNum;
}
