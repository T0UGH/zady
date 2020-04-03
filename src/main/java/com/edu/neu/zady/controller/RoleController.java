package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.util.RoleValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.List;

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
       if( roleService.updateByPIdAndUId(projectId, userId, role) == 0){
           throw new DefaultException("更新失败");
       }
    }

    @DeleteMapping("/role")
    @Auth(role = {Role.RoleEnum.MASTER}, sameProject = true)
    public void deleteRole(Integer projectId, Integer userId){
        if(roleService.deleteUser(projectId, userId) == 0){
            throw new DefaultException("删除失败");
        }
    }

    @GetMapping("/role")
    @Auth(needProject = false)
    public Role getRole(Integer projectId, Integer userId){
        Role role = roleService.selectByPIdAndUId(projectId, userId);
        if(role == null){
            throw new NotFoundException("获取失败");
        }
        return role;
    }

    @GetMapping("/userProjects")
    @Auth(needProject = false, sameUser = true)
    public List<Project> getProjectsByUser(Integer userId){
        return roleService.selectProjectsByUId(userId);
    }

    @GetMapping("/userInvites")
    @Auth(needProject = false, sameUser = true)
    public List<Project> getInviteProjectsByUser(Integer userId){
        return roleService.selectInviteProjectsByUId(userId);
    }

    @GetMapping("/projectUsers")
    @Auth(sameProject = true, role = {Role.RoleEnum.MASTER})
    public List<User> getUsersByProject(Integer projectId){
        return roleService.selectUsersByPId(projectId);
    }

    @GetMapping("/projectInvites")
    @Auth(sameProject = true, role = {Role.RoleEnum.MASTER})
    public List<User> getInviteUsersByProject(Integer projectId){
        return roleService.selectInviteUsersByPId(projectId);
    }


}
