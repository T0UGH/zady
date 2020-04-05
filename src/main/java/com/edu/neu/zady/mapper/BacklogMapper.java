package com.edu.neu.zady.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.neu.zady.pojo.Backlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BacklogMapper extends BaseMapper<Backlog> {

    @Update("UPDATE backlog SET sprint_id = null, status = '未开始' WHERE backlog_id = #{backlogId}")
    Integer removeFromCurrentSprint(Integer backlogId);
}
