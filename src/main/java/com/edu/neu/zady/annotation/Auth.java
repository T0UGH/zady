package com.edu.neu.zady.annotation;

import com.edu.neu.zady.pojo.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    boolean needLogin() default true;
    boolean needProject() default true;
    boolean sameUser() default false;
    boolean sameProject() default false;
    Role.RoleEnum[] role() default {};
}
