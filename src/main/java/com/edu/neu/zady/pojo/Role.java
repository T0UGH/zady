package com.edu.neu.zady.pojo;

import lombok.Data;

@Data
public class Role {

    private Integer id;

    private Integer projectId;

    private Integer userId;

    private String role;

    private Boolean invite;

    private Boolean delete;
}
