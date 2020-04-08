package com.edu.neu.zady.controller;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.Story;
import com.edu.neu.zady.service.StoryService;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StoryController {

    @Resource
    StoryService storyService;

    @GetMapping("/story/{storyId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    public Story getStory(@PathVariable Integer storyId){

        Story story = storyService.selectById(storyId);

        if(story == null){
            throw new NotFoundException("给定story[" + storyId + "]不存在");
        }

        return story;

    }

    @GetMapping("/stories")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    public List<Story> getStories(Integer sprintId, Integer backlogId, String statusStr){

        List<Story> storyList;

        if(sprintId != null && statusStr != null){
            storyList = storyService.selectBySprintIdAndStatus(sprintId, statusStr);
        }else if(statusStr == null){
            storyList = storyService.selectBySprintId(sprintId);
        }else{
            storyList = storyService.selectByBacklogId(backlogId);
        }

        if(storyList == null){
            throw new NotFoundException("给定story不存在");
        }

        return storyList;

    }

    @PostMapping("/story")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    public void insertStory(@RequestBody Story story){

        if(storyService.insert(story) == 0){
            throw new DefaultException("创建故事失败");
        }

    }

    @PutMapping("/story")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    public void updateStory(@RequestBody Story story){

        if(storyService.update(story) == 0){
            throw new DefaultException("更新故事失败");
        }

    }

    @DeleteMapping("/story/{storyId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    public void deleteStory(@PathVariable Integer storyId){

        if(storyService.delete(storyId) == 0){
            throw new DefaultException("删除故事失败");
        }

    }

    @PutMapping("/story/{storyId}/import")
    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner})
    public void importStory(@PathVariable Integer storyId){

        if(storyService.importStory(storyId) == 0){
            throw new DefaultException("导入故事失败");
        }

    }

    @PutMapping("/story/{storyId}/developReceive/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developReceive(@PathVariable Integer storyId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.developerReceive(storyId, userId) == 0){
            throw new DefaultException("开发人员领取故事失败");
        }

    }

    @PutMapping("/story/{storyId}/developReturn/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developReturn(@PathVariable Integer storyId, @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.developReturn(storyId, userId) == 0){
            throw new DefaultException("开发人员退回故事失败");
        }

    }

    @PutMapping("/story/{storyId}/developFinish/{userId}/{useHours}")
    @Auth(sameProject = true, role = {Role.RoleEnum.developer})
    public void developFinish(@PathVariable Integer storyId,
                              @PathVariable Integer userId,
                              @PathVariable Integer useHours){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.developFinish(storyId, userId, useHours) == 0){
            throw new DefaultException("开发人员完成故事失败");
        }

    }

    @PutMapping("/story/{storyId}/testReceive/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testReceive(@PathVariable Integer storyId,
                            @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.testReceive(storyId, userId) == 0){
            throw new DefaultException("测试人员领取故事失败");
        }

    }

    @PutMapping("/story/{storyId}/testReturn/{userId}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testReturn(@PathVariable Integer storyId,
                            @PathVariable Integer userId){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.testReturn(storyId, userId) == 0){
            throw new DefaultException("测试人员归还故事失败");
        }

    }

    @PutMapping("/story/{storyId}/testPass/{userId}/{useHours}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testPass(@PathVariable Integer storyId,
                         @PathVariable Integer userId,
                         @PathVariable Integer useHours){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.testPass(storyId, userId, useHours) == 0){
            throw new DefaultException("测试人员通过故事失败");
        }

    }

    @PutMapping("/story/{storyId}/testNotPass/{userId}/{useHours}")
    @Auth(sameProject = true, role = {Role.RoleEnum.tester})
    public void testNotPass(@PathVariable Integer storyId,
                            @PathVariable Integer userId,
                            @PathVariable Integer useHours){

        if(!ParamHolder.sameUser(userId)){
            throw new NoAuthException("给定用户[" + userId + "]无权");
        }

        if(storyService.testNotPass(storyId, userId, useHours) == 0){
            throw new DefaultException("测试人员不通过故事失败");
        }

    }

}
