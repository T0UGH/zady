package com.edu.neu.zady.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {

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

    private String token;
}
