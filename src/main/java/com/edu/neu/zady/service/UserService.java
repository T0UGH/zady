package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User selectById(Integer id);

    User selectByEmail(String email);

    List<User> selectByQueryStr(String queryStr);

    Integer update(User user);

    Integer register(User user);

    Boolean existByEmail(String email);

    Boolean existById(Integer id);

    Integer updateDefaultProjectIdToNull(Integer id);
}
