package com.edu.neu.zady.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {

    @TableId
    private Integer id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @URL
    private String avatar;

    @NotBlank
    private String password;

    private Integer defaultProjectId;

    private Integer defaultSprintId;

    @TableField(exist = false)
    private String token;
}
