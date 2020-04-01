package com.edu.neu.zady.mapper;

import com.edu.neu.zady.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    public Role selectById(Integer id);
    public Role select(Integer projectId, Integer userId);
    public Integer insert();
    public Integer update();
    public Integer delete(Integer projectId, Integer userId);
    public Integer deleteById(Integer id);
}
