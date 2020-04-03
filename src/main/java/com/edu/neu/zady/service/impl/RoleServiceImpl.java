package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.DefaultException;
import com.edu.neu.zady.mapper.RoleMapper;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.ProjectService;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Value("${zady.default-invite-role}")
    private String defaultInviteRole;

    @Resource
    RoleMapper roleMapper;

    @Resource
    ProjectService projectService;

    @Resource
    UserService userService;

    ApplicationContext applicationContext;


    @Override
    public Role selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Boolean existById(Integer id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Role::getId).eq(Role::getId, id);
        return roleMapper.selectOne(queryWrapper) != null;
    }

    @Override
    public Role selectByPIdAndUId(Integer projectId, Integer userId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getUserId, userId).eq(Role::getProjectId, projectId).eq(Role::getInvite, false);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean existByPIdAndUId(Integer projectId, Integer userId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Role::getId).eq(Role::getUserId, userId).eq(Role::getProjectId, projectId);
        logger.debug(queryWrapper.toString());
        return roleMapper.selectOne(queryWrapper) != null;
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
    public List<Role> selectByUId(Integer userId) {
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
    public Integer inviteUser(Integer projectId, Integer userId, String role) {
        //todo: 异常
        //外键检测
        if(!projectService.existById(projectId)){
            throw new BadDataException("给定Project不存在");
        }

        //外键检测
        if(!userService.existById(userId)){
            throw new BadDataException("给定User不存在");
        }

        //存在性检测，假如这个Role已经存在在数据库中，就不再执行下一步了。
        RoleService roleService = this.applicationContext.getBean(RoleService.class);
        if(roleService.existByPIdAndUId(projectId, userId)){
            throw new BadDataException("此Role已存在");
        }

        Role roleObj = new Role();
        roleObj.setUserId(userId);
        roleObj.setProjectId(projectId);
        roleObj.setRole(role);
        return roleMapper.insert(roleObj);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
