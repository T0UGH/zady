package com.edu.neu.zady.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.neu.zady.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE user SET default_project_id = null WHERE user_id = #{userId}")
    Integer updateDefaultProjectIdToNull(Integer userId);
}
