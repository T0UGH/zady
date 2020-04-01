package com.edu.neu.zady.service.impl;

import com.edu.neu.zady.mapper.ProjectMapper;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    ProjectMapper projectMapper;

    @Override
    public Project selectById(Integer id) {
        return projectMapper.selectById(id);
    }

    @Override
    public Integer insert(Project project, Integer currentUserId) {
        project.setMasterId(currentUserId);
        return projectMapper.insert(project);
    }

    @Override
    public Integer update(Project project) {
        return projectMapper.update(project);
    }

    @Override
    public Integer updateOwnerId(Integer projectId, Integer ownerId) {
        Project project = new Project();
        project.setId(projectId);
        project.setOwnerId(ownerId);
        return projectMapper.update(project);
    }

    @Override
    public Integer updateCurrentSprintId(Integer projectId, Integer currentSprintId) {
        Project project = new Project();
        project.setId(projectId);
        project.setCurrentSprintId(currentSprintId);
        return null;
    }

    @Override
    public Integer addSprintNum(Integer projectId) {
        Project project = projectMapper.selectById(projectId);
        if(project == null){
            //todo: 处理异常
            throw new RuntimeException("这个project不存在");
        }
        Integer sprintNum = project.getSprintNum();
        sprintNum += 1;
        project.setSprintNum(sprintNum);
        return projectMapper.update(project);
    }

    @Override
    public Integer updateCurrentSprintIdAndAddSprintNum(Integer projectId, Integer currentSprintId) {
        Project project = projectMapper.selectById(projectId);
        if(project == null){
            //todo: 处理异常
            throw new RuntimeException("这个project不存在");
        }
        Integer sprintNum = project.getSprintNum();
        sprintNum += 1;
        project.setCurrentSprintId(currentSprintId);
        project.setSprintNum(sprintNum);
        return projectMapper.update(project);
    }
}
