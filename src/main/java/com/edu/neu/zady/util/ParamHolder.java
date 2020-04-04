package com.edu.neu.zady.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class ParamHolder {
    public static Integer getCurrentUserId(){
        return Integer.valueOf((String) Objects.requireNonNull(RequestContextHolder
                .currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST)));
    }

    public static Integer getCurrentProjectId(){
        return Integer.valueOf((String) Objects.requireNonNull(RequestContextHolder
                .currentRequestAttributes().getAttribute("projectId", RequestAttributes.SCOPE_REQUEST)));
    }

    public static Boolean sameProject(Integer projectId){
        return Integer.valueOf((String) Objects.requireNonNull(RequestContextHolder
                .currentRequestAttributes()
                .getAttribute("projectId", RequestAttributes.SCOPE_REQUEST))).equals(projectId);
    }


    public static Boolean sameUser(Integer userId){
        return Integer.valueOf((String) Objects.requireNonNull(RequestContextHolder
                .currentRequestAttributes()
                .getAttribute("projectId", RequestAttributes.SCOPE_REQUEST))).equals(userId);
    }
}
