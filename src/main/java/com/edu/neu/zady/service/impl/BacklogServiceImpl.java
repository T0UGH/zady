package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.mapper.BacklogMapper;
import com.edu.neu.zady.pojo.Backlog;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.service.BacklogService;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.service.SprintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class BacklogServiceImpl implements BacklogService {

    @Resource
    BacklogMapper backlogMapper;

    @Resource
    ProjectService projectService;

    @Resource
    SprintService sprintService;

    @Override
    public Backlog selectById(Integer backlogId) {
        return backlogMapper.selectById(backlogId);
    }

    @Override
    public List<Backlog> selectBySprintId(Integer sprintId) {

        if(!sprintService.existById(sprintId)){
            throw new BadDataException("对应sprint" + sprintId + "不存在");
        }

        LambdaQueryWrapper<Backlog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Backlog::getSprintId, sprintId);

        return backlogMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<Backlog> selectByProjectId(Integer projectId) {

        if(!projectService.existById(projectId)){
            throw new BadDataException("对应项目不存在");
        }

        LambdaQueryWrapper<Backlog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Backlog::getProjectId, projectId);

        return backlogMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<Backlog> selectByProjectIdAndStatus(Integer projectId, String statusStr) {
        if(!projectService.existById(projectId)){
            throw new BadDataException("对应项目不存在");
        }

        Backlog.Status status;
        try{
            status = Backlog.Status.valueOf(statusStr);
        }catch (IllegalArgumentException e){
            throw new BadDataException("对应状态字段不合法");
        }

        LambdaQueryWrapper<Backlog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Backlog::getProjectId, projectId).eq(Backlog::getStatus, status);

        return backlogMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Integer insert(Backlog backlog) {
        //检查外键约束
        if(!projectService.existById(backlog.getProjectId())){
            throw new BadDataException("对应project" + backlog.getProjectId() + "不存在");
        }

        backlog.setStatus(Backlog.Status.未开始);
        return backlogMapper.insert(backlog);
    }

    @Override
    public Integer update(Backlog backlog) {
        Backlog uBacklog = new Backlog();
        uBacklog.setBacklogId(backlog.getBacklogId());
        uBacklog.setName(backlog.getName());
        uBacklog.setNote(backlog.getNote());
        uBacklog.setPriority(backlog.getPriority());
        return backlogMapper.updateById(uBacklog);
    }

    @Override
    public Integer addToCurrentSprint(Integer backlogId) {

        //先查当前的Sprint是哪一个，如果是null就报错
        Backlog backlog = backlogMapper.selectById(backlogId);
        if(backlog == null){
            throw new BadDataException("该backlog不存在，无法更新");
        }

        Integer projectId = backlog.getProjectId();
        if(projectId == null){
            throw new DefaultException("服务器内部错误，该backlog的project字段缺失，无法更新");
        }

        Project project = projectService.selectById(projectId);

        Integer sprintId = project.getCurrentSprintId();
        if(sprintId == null){
            throw new DefaultException("服务器内部错误，该project没有进行中的sprint");
        }

        if(!sprintService.existById(sprintId)){
            throw new DefaultException("服务器内部错误，该project[" + projectId +"]没有进行中的sprint[" + sprintId + "]");
        }

        //然后根据查到的sprintId，来更新backlogId的sprintId字段为这个sprintId，状态为进行中
        Backlog uBacklog = new Backlog();
        uBacklog.setBacklogId(backlogId);
        uBacklog.setSprintId(sprintId);
        uBacklog.setStatus(Backlog.Status.进行中);

        return backlogMapper.updateById(uBacklog);
    }

    @Override
    public Integer removeFromCurrentSprint(Integer backlogId) {

        Backlog backlog = backlogMapper.selectById(backlogId);
        if(backlog == null){
            throw new BadDataException("该backlog不存在，无法更新");
        }

        return backlogMapper.removeFromCurrentSprint(backlogId);
    }

    @Override
    public Integer delete(Integer backlogId) {
        return backlogMapper.deleteById(backlogId);
    }
}
