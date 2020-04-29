package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.Sprint;
import com.edu.neu.zady.service.SprintService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SprintController {

    @Resource
    SprintService sprintService;

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/sprint/{sprintId}")
    public Sprint getSprint(@PathVariable Integer sprintId){

        Sprint sprint = sprintService.selectById(sprintId);

        if(sprint == null){
            throw new NotFoundException("无此工程");
        }

        return sprint;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/sprints/{projectId}")
    public List<Sprint> getSprintList(@PathVariable Integer projectId){

        List<Sprint> sprintList = sprintService.selectByProjectId(projectId);

        if(sprintList == null){
            throw new NotFoundException("无此工程");
        }

        return sprintList;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/currentSprint/{projectId}")
    public Sprint getCurrentSprint(@PathVariable Integer projectId){

        Sprint sprint = sprintService.selectCurrentByProjectId(projectId);

        if(sprint == null){
            throw new NotFoundException("无此工程");
        }

        return sprint;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PostMapping("/sprint")
    public void startSprint(@RequestBody Sprint sprint){

        //同一个项目即可，放到service里完成鉴权

        if(sprintService.start(sprint) == 0){
            throw new DefaultException("给定sprint[" + sprint.getName() + "]无法开始");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @PutMapping("/sprint")
    public void updateSprint(@RequestBody Sprint sprint){

        //同一个项目即可，放到service里完成鉴权

        if(sprintService.update(sprint) == 0){
            throw new DefaultException("给定sprint[" + sprint.getSprintId() + "]无法更新");
        }

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    @DeleteMapping("/sprint/{sprintId}")
    public void endSprint(@PathVariable Integer sprintId){

        //同一个项目即可，放到service里完成鉴权

        if(sprintService.end(sprintId) == 0){
            throw new DefaultException("给定sprint[" + sprintId + "]无法更新");
        }

    }

}
