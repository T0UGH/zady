package com.edu.neu.zady.annotation;

import com.edu.neu.zady.pojo.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    boolean needLogin() default true;
    boolean needProject() default true;

    Role.RoleEnum[] role() default {};
}
