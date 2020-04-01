package com.edu.neu.zady.service.impl;


import com.edu.neu.zady.mapper.UserMapper;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Value("${zady.default-avatar}")
    private String defaultAvatar;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    UserMapper userMapper;

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }


    @Override
    public Integer update(User user) {
        logger.debug("Updating user: " + user.getEmail());
        String password = user.getPassword();

        if(password != null && !password.equals("")){
            user.setPassword(Encoder.string2Sha1(password));
        }

        return userMapper.update(user);
    }

    @Override
    public Integer register(User user) {
        logger.debug("Creating user: " + user.getEmail());
        String password = user.getPassword();
        String encryptPassword = Encoder.string2Sha1(password);

        user.setPassword(encryptPassword);

        if(user.getAvatar() == null){
            user.setAvatar(defaultAvatar);
        }

        return userMapper.insert(user);
    }

    @Override
    public Boolean testEmailUsed(String email) {
        User user =  userMapper.selectByEmail(email);
        return user != null && user.getId() != null && user.getEmail() != null;
    }

}
