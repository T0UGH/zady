package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

@RestController
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Auth(needProject = false)
    @PostMapping("/project")
    public void createProject(@RequestBody Project project){
        Integer currUserId = (Integer) RequestContextHolder
                .currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        if(projectService.insert(project, currUserId) == 0){
            throw new DefaultException("创建项目失败");
        }
    }

    @Auth(sameProject = true, role = {Role.RoleEnum.MASTER})
    @PutMapping("/project")
    public void updateProject(@RequestBody Project project){
        if(projectService.update(project) == 0){
            throw new DefaultException("更改失败");
        }
    }

    @Auth(sameProject = true, role = {Role.RoleEnum.MASTER})
    @PutMapping("/project/{projectId}/owner")
    public void updateProjectOwner(@PathVariable Integer projectId, Integer ownerId){
        if(projectService.updateOwnerId(projectId, ownerId) == 0){
            throw new DefaultException("更改失败");
        }
    }

    @Auth(needProject = false)
    @GetMapping("/project/{projectId}")
    public Project getProject(@PathVariable Integer projectId){
        Project project = projectService.selectById(projectId);
        if(project == null){
            throw new NotFoundException("无此工程");
        }
        return project;
    }
}
