package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.mapper.ProjectMapper;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    ProjectMapper projectMapper;

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Value("${zady.default-master-role}")
    private String defaultMasterRole;

    @Override
    public Project selectById(Integer id) {
        return projectMapper.selectById(id);
    }

    @Override
    public Boolean existById(Integer id) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Project::getId).eq(Project::getId, id);
        projectMapper.selectOne(queryWrapper);
        return projectMapper.selectById(id) != null;
    }

    @Override
    public Integer insert(Project project, Integer currentUserId) {

        User user = userService.selectById(currentUserId);

        //判断外键约素
        if(user == null){
            throw new BadDataException("这个用户不存在");
        }

        project.setMasterId(currentUserId);


        //主键填入user表的defaultProjectId属性
        int rv = projectMapper.insert(project);
        if(user.getDefaultProjectId() == null){
            user.setDefaultProjectId(project.getId());
            return userService.update(user);
        }

        //向role表中插入一条记录
        Role roleObj = new Role();
        roleObj.setProjectId(project.getId());
        roleObj.setUserId(currentUserId);
        roleObj.setInvite(false);
        roleObj.setRole(defaultMasterRole);
        return roleService.insert(roleObj);

    }

    @Override
    public Integer update(Project project) {
        return projectMapper.updateById(project);
    }


    @Override
    public Integer updateCurrentSprintId(Integer projectId, Integer currentSprintId) {
        //todo: 要检测currentSprintId是否存在
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
            throw new BadDataException("这个project不存在");
        }
        Integer sprintNum = project.getSprintNum();
        sprintNum += 1;
        project.setSprintNum(sprintNum);
        return projectMapper.updateById(project);
    }

    @Override
    public Integer updateCurrentSprintIdAndAddSprintNum(Integer projectId, Integer currentSprintId) {
        Project project = projectMapper.selectById(projectId);
        if(project == null){
            //todo: 处理异常
            throw new BadDataException("这个project不存在");
        }
        Integer sprintNum = project.getSprintNum();
        sprintNum += 1;
        project.setCurrentSprintId(currentSprintId);
        project.setSprintNum(sprintNum);
        return projectMapper.updateById(project);
    }
}
