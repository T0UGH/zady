package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.mapper.SprintMapper;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Sprint;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.service.SprintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class SprintServiceImpl implements SprintService {

    @Resource
    SprintMapper sprintMapper;

    @Resource
    ProjectService projectService;


    @Override
    public Sprint selectById(Integer sprintId) {
        return sprintMapper.selectById(sprintId);
    }

    @Override
    public Boolean existById(Integer sprintId) {
        LambdaQueryWrapper<Sprint> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Sprint::getSprintId).eq(Sprint::getSprintId, sprintId);
        return sprintMapper.selectOne(lambdaQueryWrapper) != null;
    }

    @Override
    public List<Sprint> selectByProjectId(Integer projectId) {
        if(!projectService.existById(projectId)){
            throw new BadDataException("对应项目不存在");
        }

        LambdaQueryWrapper<Sprint> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Sprint::getProjectId, projectId);
        return sprintMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Sprint selectCurrentByProjectId(Integer projectId) {

        Project project = projectService.selectById(projectId);

        if(project == null){
            throw new BadDataException("对应项目不存在");
        }

        Integer currentSprintId = project.getCurrentSprintId();

        return sprintMapper.selectById(currentSprintId);
    }

    @Override
    public Integer start(Sprint sprint) {

        Project project;

        //检测project外键约束
        if(sprint.getProjectId() != null || (project = projectService.selectById(sprint.getProjectId())) == null){
            throw new BadDataException("对应项目不存在，请修改项目Id字段");
        }

        //若project有currentSprintId，说明有sprint进行中，则报错，
        if(project.getCurrentSprintId()  != null){
            throw new BadDataException("有进行中的sprint,请先结束再创建");
        }

        //设置为进行中
        sprint.setStatus(Sprint.Status.进行中);

        //设置开始日期为今天
        sprint.setStartDate(new Date());

        //设置轮数为project.sprintNum + 1
        sprint.setRoundId(project.getSprintNum() + 1);

        int rv = sprintMapper.updateById(sprint);

        //更新project的currentSprintId为它
        if(projectService.updateCurrentSprintIdAndAddSprintNum(sprint.getProjectId(), sprint.getSprintId()) == 0){
            throw new DefaultException("服务器内部错误，无法设计当前sprint");
        }

        return rv;
    }

    @Override
    public Integer update(Sprint sprint) {
        Sprint updateSprint = new Sprint();
        updateSprint.setSprintId(sprint.getSprintId());
        updateSprint.setName(sprint.getName());
        updateSprint.setNote(sprint.getNote());
        updateSprint.setExpectedEndDate(sprint.getExpectedEndDate());
        return sprintMapper.updateById(updateSprint);
    }

    @Override
    public Integer end(Integer sprintId) {
        //先查，并判空
        Sprint sprint;
        if((sprint = sprintMapper.selectById(sprintId)) == null){
            throw new BadDataException("该sprint" + sprintId + "并不存在");
        }

        //然后判断状态
        if(sprint.getStatus() == null || sprint.getStatus() != Sprint.Status.进行中){
            throw new BadDataException("该sprint: " + sprintId + "状态非法，不能正常结束");
        }

        //然后更新为已结束，并设置结束时间
        sprint.setStatus(Sprint.Status.已完成);
        sprint.setActualEndDate(new Date());
        sprintMapper.updateById(sprint);

        //然后更新projectId的一些字段
        return projectService.updateCurrentSprintIdToNull(sprint.getProjectId());

    }
}
