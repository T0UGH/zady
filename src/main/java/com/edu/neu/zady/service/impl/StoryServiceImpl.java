package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.exception.NoAuthException;
import com.edu.neu.zady.mapper.StoryMapper;
import com.edu.neu.zady.pojo.Backlog;
import com.edu.neu.zady.pojo.Bug;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.Story;
import com.edu.neu.zady.service.*;
import com.edu.neu.zady.util.ParamHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class StoryServiceImpl implements StoryService {

    @Resource
    StoryMapper storyMapper;

    @Resource
    SprintService sprintService;

    @Resource
    BacklogService backlogService;

    @Resource
    UserService userService;

    @Resource
    BugService bugService;

    @Resource
    RoleService roleService;

    @Resource
    DashBoardService dashBoardService;

    @Resource
    BornoutService bornoutService;

    @Override
    public Story selectById(Integer storyId) {

        return storyMapper.selectById(storyId);

    }

    @Override
    public Boolean existById(Integer storyId) {

        LambdaQueryWrapper<Story> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Story::getStoryId).eq(Story::getStoryId, storyId);

        return storyMapper.selectOne(lambdaQueryWrapper) != null;
    }

    @Override
    public List<Story> selectBySprintId(Integer sprintId) {

        if(!sprintService.existById(sprintId)){
            throw new BadDataException("对应sprint[" + sprintId + "]不存在" );
        }

        LambdaQueryWrapper<Story> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Story::getSprintId, sprintId);

        return storyMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Story> selectByBacklogId(Integer backlogId) {

        if(!backlogService.existById(backlogId)){
            throw new BadDataException("对应backlog[" + backlogId + "]不存在" );
        }

        LambdaQueryWrapper<Story> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Story::getBacklogId, backlogId);

        return storyMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public List<Story> selectBySprintIdAndStatus(Integer sprintId, String statusStr) {

        if(!sprintService.existById(sprintId)){
            throw new BadDataException("对应sprint[" + sprintId + "]不存在" );
        }

        Story.Status status;
        try{
            status = Story.Status.valueOf(statusStr);
        }catch (IllegalArgumentException e){
            throw new BadDataException("对应状态字段不合法");
        }

        LambdaQueryWrapper<Story> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Story::getSprintId, sprintId).eq(Story::getStatus, status);

        return storyMapper.selectList(lambdaQueryWrapper);

    }

    @Override
    public Integer insert(Story story) {

        Integer backlogId = story.getBacklogId();

        if(backlogId == null){
            throw new BadDataException("插入请求缺少字段[backlogId]");
        }

        Backlog backlog = backlogService.selectById(backlogId);
        if(backlog.getStatus() == null || !backlog.getStatus().equals(Backlog.Status.进行中) || backlog.getSprintId() == null){
            throw new BadDataException("给定backlog[" + backlogId + "]状态不支持拆分故事操作");
        }

        if(!ParamHolder.sameProject(backlog.getProjectId())){
            throw new NoAuthException("无权操作给定项目[" + backlog.getProjectId() + "]");
        }

        story.setProjectId(backlog.getProjectId());
        story.setSprintId(backlog.getSprintId());
        story.setStatus(Story.Status.待导入);
        story.setCreatedDate(new Date());

        return storyMapper.insert(story);
    }

    @Override
    public Integer update(Story story) {

        Story dbStory = storyMapper.selectById(story.getStoryId());

        if(dbStory == null){
            throw new BadDataException("给定story[" + story.getStoryId() + "]不存在");
        }

        if(!ParamHolder.sameProject(dbStory.getProjectId())){
            throw new NoAuthException("无权操作此project[" + dbStory.getProjectId() + "]");
        }

        Story uStory = new Story();
        uStory.setStoryId(story.getStoryId());
        uStory.setName(story.getName());
        uStory.setExpectedHours(story.getExpectedHours());

        return storyMapper.updateById(uStory);

    }

    @Override
    public Integer delete(Integer storyId) {

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!story.getStatus().equals(Story.Status.待导入)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持删除操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        return storyMapper.deleteById(storyId);

    }

    //导入
    public Integer importStory(Integer storyId) {

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!story.getStatus().equals(Story.Status.待导入)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持导入操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        story.setStatus(Story.Status.待完成);

        if(dashBoardService.addStoryNum(story.getSprintId()) == 0){
            throw new DefaultException("服务器内部错误，无法为该story,[" + storyId + "],更新dashboard信息");
        }

        return storyMapper.updateById(story);
    }

    //打回
    public Integer returnStory(Integer storyId){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!story.getStatus().equals(Story.Status.待完成)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持打回操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        story.setStatus(Story.Status.待导入);

        if(dashBoardService.subStoryNum(story.getSprintId()) == 0){
            throw new DefaultException("服务器内部错误，无法为该story,[" + storyId + "],更新dashboard信息");
        }

        return storyMapper.updateById(story);

    }

    //开发领取
    public Integer developerReceive(Integer storyId, Integer userId){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!userService.existById(userId)){
            throw new BadDataException("给定user[" + userId + "]不存在");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }


        Role role = roleService.selectByPIdAndUId(story.getProjectId(), userId);
        if(role == null || !role.getRole().contains(Role.RoleEnum.developer.name())){
            throw new NoAuthException("给定user[" + userId + "]不具有给定project[" + story.getProjectId() + "]的developer权限");
        }

        if(!story.getStatus().equals(Story.Status.待完成)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持开发人员领取操作");
        }

        story.setDeveloperId(userId);
        story.setStatus(Story.Status.完成中);

        return storyMapper.updateById(story);

    }

    //developReturn开发人员撤销领取
    public Integer developReturn(Integer storyId, Integer userId){
        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(story.getDeveloperId() == null || !story.getDeveloperId().equals(userId)){
            throw new BadDataException("给定user[" + userId + "]无权执行撤销领取操作");
        }

        if(!story.getStatus().equals(Story.Status.完成中)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持开发人员撤销领取操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        return storyMapper.developReturn(storyId);
    }

    //developFinish开发人员完成
    public Integer developFinish(Integer storyId, Integer userId, Integer useHours){
        Story story = storyMapper.selectById(storyId);
        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(story.getDeveloperId() == null || !story.getDeveloperId().equals(userId)){
            throw new BadDataException("给定user[" + userId + "]无权执行开发完成操作");
        }

        if(!story.getStatus().equals(Story.Status.完成中)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持开发完成操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        //判断有没有bug，只有没有bug或者所有的bug全都是待复核或已完成状态，才能提交
        List<Bug> bugList = bugService.selectByStoryId(storyId);
        for (Bug bug:bugList) {
            if(!bug.getStatus().equals(Bug.Status.待复核) && !bug.getStatus().equals(Bug.Status.已完成)){
                throw new BadDataException("给定story[" + storyId + "]存在未提交修改的BUG，无法修改为待测试状态");
            }
        }

        story.setStatus(Story.Status.待测试);
        story.setCurrentHours(story.getCurrentHours() + useHours);

        return storyMapper.updateById(story);
    }

    //testReceive 测试领取
    public Integer testReceive(Integer storyId, Integer userId){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(!userService.existById(userId)){
            throw new BadDataException("给定user[" + userId + "]不存在");
        }


        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        if(!story.getStatus().equals(Story.Status.待测试)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持测试人员领取操作");
        }

        if(story.getTesterId() != null && !story.getTesterId().equals(userId)){
            throw new BadDataException("给定story[" + storyId + "]只能由测试人员[" + story.getTesterId() + "]领取");
        }

        story.setTesterId(userId);
        story.setStatus(Story.Status.测试中);

        return storyMapper.updateById(story);

    }

    //testReturn测试人员撤销领取
    public Integer testReturn(Integer storyId, Integer userId){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(story.getTesterId() == null || !story.getTesterId().equals(userId)){
            throw new BadDataException("给定user[" + userId + "]无权执行撤销领取操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        if(!story.getStatus().equals(Story.Status.测试中)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持测试人员撤销领取操作");
        }

        return storyMapper.testReturn(storyId);

    }

    public Integer testPass(Integer storyId, Integer userId, Integer useHours){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(story.getTesterId() == null || !story.getTesterId().equals(userId)){
            throw new BadDataException("给定user[" + userId + "]无权执行通过测试操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        if(!story.getStatus().equals(Story.Status.测试中)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持通过测试操作");
        }

        List<Bug> bugList = bugService.selectByStoryId(storyId);
        for (Bug bug:bugList) {
            if(!bug.getStatus().equals(Bug.Status.已完成)){
                throw new BadDataException("给定story[" + storyId + "]存在未提交修改的BUG，无法修改为待测试状态");
            }
        }

        story.setStatus(Story.Status.已完成);
        story.setFinishedDate(new Date());
        story.setCurrentHours(story.getCurrentHours() + useHours);

        if(bornoutService.addBornout(story.getSprintId(), new Date()) == 0){
            throw new DefaultException("服务器内部错误，无法为该story,[" + storyId + "],更新bornout信息");
        }

        if(story.getExpectedHours() >= story.getCurrentHours()){
            if(dashBoardService.addInTimeStoryNum(story.getSprintId()) == 0){
                throw new DefaultException("服务器内部错误，无法为该story,[" + storyId + "],更新dashboard信息");
            }
        }else{
            if(dashBoardService.addOutTimeStoryNum(story.getSprintId()) == 0){
                throw new DefaultException("服务器内部错误，无法为该story,[" + storyId + "],更新dashboard信息");
            }
        }

        return storyMapper.updateById(story);

    }

    public Integer testNotPass(Integer storyId, Integer userId, Integer useHours){

        Story story = storyMapper.selectById(storyId);

        if(story == null){
            throw new BadDataException("给定story[" + storyId + "]不存在");
        }

        if(story.getTesterId() == null || !story.getTesterId().equals(userId)){
            throw new BadDataException("给定user[" + userId + "]无权执行未通过测试操作");
        }

        if(!story.getStatus().equals(Story.Status.测试中)){
            throw new BadDataException("给定story[" + storyId + "]状态不支持未通过测试操作");
        }

        if(!ParamHolder.sameProject(story.getProjectId())){
            throw new NoAuthException("无权操作此project[" + story.getProjectId() + "]");
        }

        List<Bug> bugList = bugService.selectByStoryId(storyId);
        for (Bug bug:bugList) {
            if(!bug.getStatus().equals(Bug.Status.已完成) && !bug.getStatus().equals(Bug.Status.待确认)){
                throw new BadDataException("给定story[" + storyId + "]存在未提交修改的BUG，无法修改为完成中状态");
            }
        }

        story.setStatus(Story.Status.完成中);
        story.setCurrentHours(story.getCurrentHours() + useHours);

        return storyMapper.updateById(story);

    }

}
