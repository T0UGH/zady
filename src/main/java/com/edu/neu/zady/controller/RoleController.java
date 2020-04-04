package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.util.ParamHolder;
import com.edu.neu.zady.util.RoleValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("/role")
    @Auth(role = {Role.RoleEnum.master}, sameProject = true)
    public void inviteUser(Integer projectId, Integer userId, String role){
        if(!RoleValidator.validate(role)){
            throw new BadDataException("角色信息格式错误");
        }

        if(roleService.inviteUser(projectId, userId, role) == 0){
            throw new DefaultException("角色创建失败");
        }
    }

    @PutMapping("/invite/{projectId}")
    @Auth(needProject = false)
    public void acceptInvite(@PathVariable Integer projectId){
        Integer currUserId = ParamHolder.getCurrentUserId();

        if(currUserId == null){
            throw new DefaultException("用户ID错误");
        }

        if(roleService.joinProject(projectId, currUserId) == 0){
            throw new DefaultException("接受邀请失败");
        }
    }

    @PutMapping("/role")
    @Auth(role = {Role.RoleEnum.master}, sameProject = true)
    public void updateRole(Integer projectId, Integer userId, String role){
       if( roleService.updateByPIdAndUId(projectId, userId, role) == 0){
           throw new DefaultException("更新失败");
       }
    }

    @DeleteMapping("/role")
    @Auth(role = {Role.RoleEnum.master}, sameProject = true)
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

    @GetMapping("/user/projects/{userId}")
    @Auth(needProject = false)
    public List<Project> getProjectsByUser(@PathVariable Integer userId){
        return roleService.selectProjectsByUId(userId);
    }

    @GetMapping("/user/invites/{userId}")
    @Auth(needProject = false)
    public List<Project> getInviteProjectsByUser(@PathVariable Integer userId){
        return roleService.selectInviteProjectsByUId(userId);
    }

    @GetMapping("/project/users/{projectId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.master})
    public List<User> getUsersByProject(@PathVariable Integer projectId){
        if(!ParamHolder.sameProject(projectId)){
            throw new NoAuthException("无当前项目权限");
        }
        return roleService.selectUsersByPId(projectId);
    }

    @GetMapping("/project/invites/{projectId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.master})
    public List<User> getInviteUsersByProject(@PathVariable Integer projectId){
        if(!ParamHolder.sameProject(projectId)){
            throw new NoAuthException("无当前项目权限");
        }
        return roleService.selectInviteUsersByPId(projectId);
    }

}
