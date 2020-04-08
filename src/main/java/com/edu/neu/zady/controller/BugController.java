package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Bug;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.BugService;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BugController {

    @Resource
    BugService bugService;

    @GetMapping("/bug/{bugId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    public Bug getBug(@PathVariable Integer bugId){

        Bug bug = bugService.selectById(bugId);

        if(bug == null){
            throw new NotFoundException("给定bug[" + bugId + "]不存在");
        }

        return bug;

    }

    @GetMapping("/bugs")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    public List<Bug> getBugs(Integer sprintId, Integer backlogId, Integer storyId, String statusStr){

        List<Bug> bugList;

        boolean statusNotNull = statusStr != null && !statusStr.equals("");

        if(storyId != null && statusNotNull){
            bugList = bugService.selectByStoryIdAndStatus(storyId, statusStr);
        }else if(storyId != null){
            bugList = bugService.selectByStoryId(storyId);
        }else if(backlogId != null && statusNotNull){
            bugList = bugService.selectByBacklogIdAndStatus(backlogId, statusStr);
        }else if(backlogId != null){
            bugList = bugService.selectByBacklogId(backlogId);
        }else if(sprintId != null && statusNotNull){
            bugList = bugService.selectBySprintIdAndStatus(sprintId, statusStr);
        }else if(sprintId != null){
            bugList = bugService.selectBySprintId(sprintId);
        }else {
            throw new BadDataException("请求bug列表无任何参数");
        }

        if(bugList == null){
            throw new NotFoundException("给定查询条件无可用bug列表");
        }

        return bugList;

    }

    @PostMapping("/bug")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void createBug(@RequestBody Bug bug){

        if(bugService.insert(bug) == 0){
            throw new DefaultException("测试人员提交bug失败");
        }

    }

    @PutMapping("/bug")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void updateBug(@RequestBody Bug bug){

        if(bugService.update(bug) == 0){
            throw new DefaultException("测试人员更新bug失败");
        }

    }

    @DeleteMapping("/bug/{bugId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void deleteBug(@PathVariable Integer bugId){

        if(bugService.delete(bugId) == 0){
            throw new DefaultException("测试人员删除bug失败");
        }

    }

    @PutMapping("/bug/{bugId}/developNotConfirm/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developNotConfirm(@PathVariable Integer bugId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(bugService.developNotConfirm(bugId, userId) == 0){
            throw new DefaultException("开发人员不接受bug失败");
        }

    }

    @PutMapping("/bug/{bugId}/developConfirm/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developConfirm(@PathVariable Integer bugId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(bugService.developConfirm(bugId, userId) == 0){
            throw new DefaultException("测试人员接受bug失败");
        }

    }

    @PutMapping("/bug/{bugId}/developFinish/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developFinish(@PathVariable Integer bugId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(bugService.developFinish(bugId, userId) == 0){
            throw new DefaultException("开发人员完成bug修改失败");
        }

    }

    @PutMapping("/bug/{bugId}/testPass/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testPass(@PathVariable Integer bugId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(bugService.testPass(bugId, userId) == 0){
            throw new DefaultException("测试人员通过bug验证失败");
        }

    }

    @PutMapping("/bug/{bugId}/testNotPass/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testNotPass(@PathVariable Integer bugId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(bugService.testNotPass(bugId, userId) == 0){
            throw new DefaultException("测试人员不通过bug验证失败");
        }

    }

}
