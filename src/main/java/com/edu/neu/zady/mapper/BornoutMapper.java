package com.edu.neu.zady.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.neu.zady.pojo.Bornout;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BornoutMapper extends BaseMapper<Bornout> {

    Bornout selectBySprintAndDate(Integer sprintId, String createDateStr);

}
