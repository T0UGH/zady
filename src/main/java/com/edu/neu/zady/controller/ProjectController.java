package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Auth(needProject = false)
    @PostMapping("/project")
    public void createProject(@RequestBody Project project){
        Integer currUserId = ParamHolder.getCurrentUserId();
        if(projectService.insert(project, currUserId) == 0){
            throw new DefaultException("创建项目失败");
        }
    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master})
    @PutMapping("/project")
    public void updateProject(@RequestBody Project project){
        if(!ParamHolder.sameProject(project.getProjectId())){
            throw new NoAuthException("无权操作此项目");
        }
        if(projectService.update(project) == 0){
            throw new DefaultException("更改失败");
        }
    }

    @Auth(needProject = false)
    @GetMapping("/project")
    public Project getProject(Integer projectId){
        Project project = projectService.selectById(projectId);
        if(project == null){
            throw new NotFoundException("无此工程");
        }
        return project;
    }
}
