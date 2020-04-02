package com.edu.neu.zady.mapper;

import com.edu.neu.zady.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    Role selectById(Integer id);
    Role select(Integer projectId, Integer userId);
    Integer insert();
    Integer update();
    Integer delete(Integer projectId, Integer userId);
    Integer deleteById(Integer id);
}
