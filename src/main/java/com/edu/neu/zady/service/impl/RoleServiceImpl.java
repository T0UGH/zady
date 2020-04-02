package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.neu.zady.mapper.RoleMapper;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Value("${zady.default-invite-role}")
    private String defaultInviteRole;

    @Resource
    RoleMapper roleMapper;

    @Resource
    ProjectService projectService;

    @Resource
    UserService userService;


    @Override
    public Role selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> selectByPId(Integer projectId) {
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().lambda();
        queryWrapper.eq(Role::getProjectId, projectId);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public List<Role> selectInviteByUId(Integer userId) {
        LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().lambda();
        queryWrapper.eq(Role::getUserId, userId).eq(Role::getInvite, true);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public List<Role> selectJoinByUId(Integer userId) {
      LambdaQueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().lambda();
        queryWrapper.eq(Role::getUserId, userId).eq(Role::getInvite, false);
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    public Integer joinProject(Integer projectId, Integer userId) {

        Role queryRole = new Role();
        queryRole.setUserId(userId);
        queryRole.setProjectId(projectId);

        Role updateRole = new Role();
        updateRole.setInvite(false);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(queryRole);
        return roleMapper.update(updateRole, queryWrapper);
    }

    @Override
    public Integer deleteUser(Integer projectId, Integer userId) {

        Role queryRole = new Role();
        queryRole.setUserId(userId);
        queryRole.setProjectId(projectId);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(queryRole);
        return roleMapper.delete(queryWrapper);
    }

    @Override
    public Integer updateByPIdAndUId(Integer projectId, Integer userId, String role) {
        Role queryRole = new Role();
        queryRole.setUserId(userId);
        queryRole.setProjectId(projectId);
        Role updateRole = new Role();
        updateRole.setRole(role);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>(queryRole);
        return roleMapper.update(updateRole, queryWrapper);
    }

    @Override
    public Integer inviteUser(Integer projectId, Integer userId) {
        return inviteUser(projectId, userId, defaultInviteRole);
    }

    @Override
    public Integer inviteUser(Integer userId, Integer projectId, String role) {
        if(!projectService.existById(projectId)){
            throw new RuntimeException("给定Project不存在");
        }

        if(!userService.existById(userId)){
            throw new RuntimeException("给定User不存在");
        }

        Role roleObj = new Role();
        roleObj.setUserId(userId);
        roleObj.setProjectId(projectId);
        roleObj.setRole(role);
        return roleMapper.insert(roleObj);
    }


}
