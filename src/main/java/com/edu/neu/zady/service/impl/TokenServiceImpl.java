package com.edu.neu.zady.service.impl;

import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.RoleService;
import com.edu.neu.zady.service.TokenService;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.Encoder;
import com.edu.neu.zady.util.TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Transactional
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Override
    public User login(String email, String password) {
        User user = userService.selectByEmail(email);

        if(user == null || user.getUserId() == null || user.getPassword() == null){
            throw new NotFoundException("用户不存在");
        }

        if(!Encoder.string2Sha1(password).equals(user.getPassword())){
            throw new BadDataException("密码错误");
        }

        Integer projectId;
        String role = null;
        if((projectId = user.getDefaultProjectId()) != null){
            Role roleObj = roleService.selectByPIdAndUId(projectId, user.getUserId());
            if(roleObj != null){
                role = roleObj.getRole();
                user.setRole(roleObj);
            }else{
                projectId = null;
            }
        }

        String token = TokenGenerator.generate(user.getUserId(), projectId, role);
        user.setToken(token);
        user.setPassword(null);
        return user;
    }

    @Override
    public String switchProject(Integer userId, Integer projectId) {
        Role roleObj = roleService.selectByPIdAndUId(projectId, userId);
        if(roleObj == null){
            throw new BadDataException("未加入此项目");
        }
        return TokenGenerator.generate(userId, projectId, roleObj.getRole());
    }
}
