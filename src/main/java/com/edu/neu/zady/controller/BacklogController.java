package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Backlog;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.BacklogService;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BacklogController {

    @Resource
    BacklogService backlogService;

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/backlog/{backlogId}")
    public Backlog getBacklog(@PathVariable Integer backlogId){

        Backlog backlog = backlogService.selectById(backlogId);

        if(backlog == null){
            throw new NotFoundException("无此项目[" + backlogId + "]");
        }

        return backlog;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/backlogs")
    public List<Backlog> getBacklogs(Integer projectId, Integer sprintId, String statusStr){

        boolean statusNotNull = statusStr != null && !statusStr.equals("");

        List<Backlog> backlogList;

        if(sprintId != null && statusNotNull){
            backlogList = backlogService.selectBySprintIdAndStatus(sprintId, statusStr);
        }else if(sprintId != null){
            backlogList = backlogService.selectBySprintId(sprintId);
        }else if(projectId != null && statusNotNull){
            backlogList = backlogService.selectByProjectIdAndStatus(projectId, statusStr);
        }else if(projectId != null){
            backlogList = backlogService.selectByProjectId(projectId);
        }else{
            throw new BadDataException("既未提供字段[projectId]也未提供字段[sprintId]，无法查询");
        }

        return backlogList;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PostMapping("/backlog")
    public void createBacklog(@RequestBody Backlog backlog){

        //因为auth无法读取requestBody，所有只能在这里添加额外的鉴权操作
        if(!ParamHolder.sameProject(backlog.getProjectId())){
            throw new NoAuthException("无给定project[" + backlog.getProjectId() + "]权限");
        }

        if(backlogService.insert(backlog) == 0){
            throw new DefaultException("创建项目失败");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PutMapping("/backlog")
    public void updateBacklog(@RequestBody Backlog backlog) {

        if(backlogService.update(backlog) == 0){
            throw new DefaultException("更新项目失败");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PostMapping("/currentBacklog/{backlogId}")
    public void addToCurrentSprint(@PathVariable Integer backlogId){

        if(backlogService.addToCurrentSprint(backlogId) == 0){
            throw new DefaultException("添加到当前迭代失败");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @DeleteMapping("/currentBacklog/{backlogId}")
    public void removeFromCurrentSprint(@PathVariable Integer backlogId){

        if(backlogService.removeFromCurrentSprint(backlogId) == 0){
            throw new DefaultException("添加到当前迭代失败");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @DeleteMapping("/backlog/{backlogId}")
    public void delete(@PathVariable Integer backlogId){

        if(backlogService.delete(backlogId) == 0){
            throw new DefaultException("删除失败");
        }
    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PutMapping("/backlog/finish/{backlogId}")
    public void finish(@PathVariable Integer backlogId){

        if(backlogService.finish(backlogId) == 0){
            throw new DefaultException("完成失败");
        }

    }

}
