package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Role {

    @TableId
    private Integer id;

    private Integer projectId;

    private Integer userId;

    private String role;

    private Boolean invite;

    @TableLogic
    private Boolean flag;

    public enum RoleEnum {
        master, owner, developer, tester
    }
}
