package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class Project {

    @TableId
    private Integer id;

    @NotBlank
    private String name;

    private String note;

    @URL
    private String githubUrl;

    private Integer ownerId;

    private Integer masterId;

    private Integer sprintNum;

    private Integer currentSprintId;

    @TableField(exist = false)
    private Role role;
}
