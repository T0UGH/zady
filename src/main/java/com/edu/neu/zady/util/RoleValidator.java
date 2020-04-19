package com.edu.neu.zady.util;

import com.edu.neu.zady.pojo.Role;


public class RoleValidator {
    public static boolean validate(String role){
        if(role == null){
            return false;
        }
        String[] roles = role.split(",");
        for (String s:roles) {
            Role.RoleEnum[] roleEnums = Role.RoleEnum.values();
            boolean flag = false;
            for (Role.RoleEnum roleEnum: roleEnums) {
                if(roleEnum.name().equals(s)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return false;
            }
        }
        return true;
    }
}
