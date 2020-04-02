package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理人员和项目之间的关系的服务
 * */
@Service
public interface RoleService {

    /**
     * 根据id获取Role
     * */
    Role selectById(Integer id);


    /**
     * 根据id判断Role是否存在
     * */
    Boolean existById(Integer id);


    /**
     * 根据projectId和userId获取Role
     * */
    Role selectByPIdAndUId(Integer projectId, Integer userId);

    /**
     * 根据projectId和userId判断Role是否存在
     * */
    Boolean existByPIdAndUId(Integer projectId, Integer userId);

    /**
     * 根据项目的id(projectId)获取一个Role的列表(删除过的不算)
     * */
    List<Role> selectByPId(Integer projectId);

    /**
     * 获取人员加入过的项目列表（删除过的不算）
     * */
    List<Role> selectInviteByUId(Integer userId);

    /**
     * 获取人员被邀请的项目
     * */
    List<Role> selectByUId(Integer userId);

    /**
     * 人员加入项目
     * */
    Integer joinProject(Integer projectId, Integer userId);

    /**
     * 从项目中删除人员
     * */
    Integer deleteUser(Integer projectId, Integer userId);

    /**
     * 更新项目中人员的角色
     * */
    Integer updateByPIdAndUId(Integer projectId, Integer userId, String role);

    /**
     * 邀请人员加入项目，并且给人员赋予配置文件中的默认权限
     * */
    Integer inviteUser(Integer projectId, Integer userId);

    /**
     * 邀请人员加入项目
     * */
    Integer inviteUser(Integer projectId, Integer userId,  String role);
}
