package com.edu.neu.zady.mapper;

import com.edu.neu.zady.pojo.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {
    Project selectById(Integer id);
    Integer insert(Project project);
    Integer update(Project project);
}
