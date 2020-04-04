package com.edu.neu.zady.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.mapper.UserMapper;
import com.edu.neu.zady.pojo.User;
import com.edu.neu.zady.service.UserService;
import com.edu.neu.zady.util.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
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
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<User> selectByQueryStr(String queryStr) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getEmail, queryStr).or().like(User::getName, queryStr);
        return userMapper.selectList(queryWrapper);
    }

    public Boolean existById(Integer id){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getUserId).eq(User::getUserId, id);
        return userMapper.selectOne(lambdaQueryWrapper) != null;
    }

    @Override
    public Integer updateDefaultProjectIdToNull(Integer id) {
        return userMapper.updateDefaultProjectIdToNull(id);
    }

    @Override
    public Integer update(User user) {
        logger.debug("Updating user: " + user.getEmail());
        String password = user.getPassword();

        if(password != null && !password.equals("")){
            user.setPassword(Encoder.string2Sha1(password));
        }

        return userMapper.updateById(user);
    }

    @Override
    public Integer register(User user) {
        logger.debug("Creating user: " + user.getEmail());

        //密码进入数据库前加密
        String password = user.getPassword();
        String encryptPassword = Encoder.string2Sha1(password);

        user.setPassword(encryptPassword);

        if(user.getAvatar() == null){
            user.setAvatar(defaultAvatar);
        }

        return userMapper.insert(user);
    }

    @Override
    public Boolean existByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user =  userMapper.selectOne(queryWrapper);
        return user != null && user.getUserId() != null && user.getEmail() != null;
    }

}
