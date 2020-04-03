package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.util.RoleValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

@RestController
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("/role")
    @Auth(role = {Role.RoleEnum.MASTER}, sameProject = true)
    public void inviteUser(Integer userId, Integer projectId, String role){
        if(!RoleValidator.validate(role)){
            throw new BadDataException("角色信息格式错误");
        }

        if(roleService.inviteUser(userId, projectId, role) == 0){
            throw new DefaultException("角色创建失败");
        }
    }

    @PutMapping("/invite/{projectId}")
    @Auth(needProject = false)
    public void acceptInvite(@PathVariable Integer projectId){
        Integer currUserId = (Integer) RequestContextHolder
                .currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        if(currUserId == null){
            throw new DefaultException("用户ID错误");
        }

        if(roleService.joinProject(projectId, currUserId) == 0){
            throw new DefaultException("接受邀请失败");
        }
    }

    @PutMapping("/role")
    @Auth(role = {Role.RoleEnum.MASTER}, sameProject = true)
    public void updateRole(Integer projectId, Integer userId, String role){
        roleService.updateByPIdAndUId(projectId, userId, role);
    }
}
