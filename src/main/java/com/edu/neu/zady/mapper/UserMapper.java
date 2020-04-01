package com.edu.neu.zady.mapper;

import com.edu.neu.zady.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(Integer id);
    User selectByEmail(String email);
    int insert(User user);
    int delete(User user);
    int update(User user);
}
