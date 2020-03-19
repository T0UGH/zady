package com.edu.neu.zady.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class Project {

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
}
