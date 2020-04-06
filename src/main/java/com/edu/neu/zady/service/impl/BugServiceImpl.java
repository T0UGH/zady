package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.mapper.BugMapper;
import com.edu.neu.zady.pojo.Bug;
import com.edu.neu.zady.pojo.Story;
import com.edu.neu.zady.service.BacklogService;
import com.edu.neu.zady.service.BugService;
import com.edu.neu.zady.service.SprintService;
import com.edu.neu.zady.service.StoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class BugServiceImpl implements BugService {

    @Resource
    BugMapper bugMapper;

    @Resource
    SprintService sprintService;

    @Resource
    BacklogService backlogService;

    @Resource
    StoryService storyService;

    @Override
    public Bug selectById(Integer bugId) {

        return bugMapper.selectById(bugId);

    }

    @Override
    public List<Bug> selectBySprintId(Integer sprintId) {

        if(!sprintService.existById(sprintId)){
            throw new BadDataException("给定sprint[" + sprintId + "]不存在");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getSprintId, sprintId);

        return bugMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Bug> selectBySprintIdAndStatus(Integer sprintId, String statusStr) {

        if(!sprintService.existById(sprintId)){
            throw new BadDataException("给定sprint[" + sprintId + "]不存在");
        }

        Bug.Status status;
        try{
            status = Bug.Status.valueOf(statusStr);
        }catch (IllegalArgumentException e){
            throw new BadDataException("对应状态字段不合法");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getSprintId, sprintId).eq(Bug::getStatus, status);

        return bugMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Bug> selectByBacklogId(Integer backlogId) {

        if(!backlogService.existById(backlogId)){
            throw new BadDataException("给定backlog[" + backlogId + "]不存在");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getBacklogId, backlogId);

        return bugMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Bug> selectByBacklogIdAndStatus(Integer backlogId, String statusStr) {

        if(!backlogService.existById(backlogId)){
            throw new BadDataException("给定backlog[" + backlogId + "]不存在");
        }

        Bug.Status status;
        try{
            status = Bug.Status.valueOf(statusStr);
        }catch (IllegalArgumentException e){
            throw new BadDataException("对应状态字段不合法");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getBacklogId, backlogId).eq(Bug::getStatus, status);

        return bugMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Bug> selectByStoryId(Integer storyId) {

        if(!storyService.existById(storyId)){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getStoryId, storyId);

        return bugMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<Bug> selectByStoryIdAndStatus(Integer storyId, String statusStr) {

        if(!storyService.existById(storyId)){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        Bug.Status status;
        try{
            status = Bug.Status.valueOf(statusStr);
        }catch (IllegalArgumentException e){
            throw new BadDataException("对应状态字段不合法");
        }

        LambdaQueryWrapper<Bug> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Bug::getStoryId, storyId).eq(Bug::getStatus, status);

        return bugMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public Integer insert(Bug bug) {

        Integer storyId = bug.getStoryId();
        Story story;

        if(storyId == null || (story = storyService.selectById(storyId)) == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!story.getStatus().equals(Story.Status.测试中)){
            throw new BadDataException("给定story[" + storyId + "]不处于测试中状态，无法测试");
        }

        bug.setProjectId(story.getProjectId());
        bug.setBacklogId(story.getBacklogId());
        bug.setSprintId(story.getSprintId());
        bug.setStatus(Bug.Status.待确认);
        bug.setDeveloperId(story.getDeveloperId());
        bug.setTesterId(story.getTesterId());

        return bugMapper.insert(bug);

    }

    @Override
    public Integer update(Bug bug) {

        Bug uBug = new Bug();
        uBug.setBugId(bug.getBugId());
        uBug.setName(bug.getName());
        uBug.setNote(bug.getNote());
        uBug.setImageUrl(bug.getImageUrl());
        uBug.setLevel(bug.getLevel());

        return bugMapper.updateById(uBug);

    }

    @Override
    public Integer delete(Integer bugId) {

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getStatus() != Bug.Status.待确认 || bug.getStatus() != Bug.Status.已完成){
            throw new BadDataException("给定bug[" + bugId + "]状态无法删除");
        }

        return bugMapper.deleteById(bugId);

    }

    //开发人员确认非错
    public Integer developConfirmNot(Integer bugId, Integer userId){

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getDeveloperId() == null || !bug.getDeveloperId().equals(userId)){
            throw new BadDataException("给定user[" +userId + "]无权操作此bug[" + bugId + "]");
        }

        if(!bug.getStatus().equals(Bug.Status.待确认)){
            throw new BadDataException("给定bug[" + bugId + "]状态无法执行确认非错操作");
        }

        bug.setStatus(Bug.Status.已完成);
        return bugMapper.updateById(bug);

    }

    //开发人员确认有错
    public Integer developConfirmOk(Integer bugId, Integer userId){

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getDeveloperId() == null || !bug.getDeveloperId().equals(userId)){
            throw new BadDataException("给定user[" +userId + "]无权操作此bug[" + bugId + "]");
        }

        if(!bug.getStatus().equals(Bug.Status.待确认)){
            throw new BadDataException("给定bug[" + bugId + "]状态无法执行确认为错操作");
        }

        bug.setStatus(Bug.Status.待修改);
        return bugMapper.updateById(bug);
    }


    //开发人员修改完成
    public Integer developFinish(Integer bugId, Integer userId){

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getDeveloperId() == null || !bug.getDeveloperId().equals(userId)){
            throw new BadDataException("给定user[" +userId + "]无权操作此bug[" + bugId + "]");
        }

        if(!bug.getStatus().equals(Bug.Status.待修改)){
            throw new BadDataException("给定bug[" + bugId + "]状态无法执行修改完成操作");
        }

        bug.setStatus(Bug.Status.待复核);
        return bugMapper.updateById(bug);

    }

    //测试人员复核通过
    public Integer testPass(Integer bugId, Integer userId){

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getDeveloperId() == null || !bug.getTesterId().equals(userId)){
            throw new BadDataException("给定user[" +userId + "]无权操作此bug[" + bugId + "]");
        }

        if(!bug.getStatus().equals(Bug.Status.待复核)){
            throw new BadDataException("给定bug[" + bugId + "]状态无法执行测试人员复核通过操作");
        }

        bug.setStatus(Bug.Status.已完成);
        return bugMapper.updateById(bug);

    }

    //测试人员复核不通过
    public Integer testNotPass(Integer bugId, Integer userId){

        Bug bug = bugMapper.selectById(bugId);

        if(bug == null){
            throw new BadDataException("给定bug[" + bugId + "]不存在");
        }

        if(bug.getDeveloperId() == null || !bug.getTesterId().equals(userId)){
            throw new BadDataException("给定user[" +userId + "]无权操作此bug[" + bugId + "]");
        }

        if(!bug.getStatus().equals(Bug.Status.待复核)){
            throw new BadDataException("给定bug[" + bugId + "]状态无法执行测试人员复核不通过操作");
        }

        bug.setStatus(Bug.Status.待确认);
        return bugMapper.updateById(bug);

    }
}
