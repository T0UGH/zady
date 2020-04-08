package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Bornout {

    @TableId
    private Integer bornoutId;

    private Integer sprintId;

    private Date createDate;

    private Integer finishedStoryNum;
}
