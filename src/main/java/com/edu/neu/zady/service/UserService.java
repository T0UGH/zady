package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.User;

public interface UserService {
    User selectById(Integer id);
    User selectByEmail(String email);
    Integer update(User user);
    Integer register(User user);
    Boolean testEmailUsed(String email);
}
