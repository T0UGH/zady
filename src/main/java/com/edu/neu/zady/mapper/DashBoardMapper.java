package com.edu.neu.zady.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.neu.zady.pojo.DashBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashBoardMapper extends BaseMapper<DashBoard> {

    @Select("\n" +
            "SELECT sprint_id,\n" +
            "\t\t   SUM(backlog_num) AS backlog_num, \n" +
            "\t\t\t SUM(finished_backlog_num) AS finished_backlog_num,\n" +
            "\t\t\t SUM(story_num) AS story_num,\n" +
            "\t\t\t SUM(finished_story_num) AS finished_story_num,\n" +
            "\t\t\t SUM(bug_num) AS bug_num,\n" +
            "\t\t\t SUM(solved_bug_num) AS solved_bug_num,\n" +
            "\t\t\t SUM(in_time_story_num) AS in_time_story_num,\n" +
            "\t\t\t SUM(out_time_story_num) AS out_time_story_num\n" +
            "FROM dash_board\n" +
            "WHERE sprint_id = #{sprintId}")
    DashBoard selectBySprint(Integer sprintId);

    @Select("\n" +
            "SELECT project_id,\n" +
            "\t\t   SUM(backlog_num) AS backlog_num, \n" +
            "\t\t\t SUM(finished_backlog_num) AS finished_backlog_num,\n" +
            "\t\t\t SUM(story_num) AS story_num,\n" +
            "\t\t\t SUM(finished_story_num) AS finished_story_num,\n" +
            "\t\t\t SUM(bug_num) AS bug_num,\n" +
            "\t\t\t SUM(solved_bug_num) AS solved_bug_num,\n" +
            "\t\t\t SUM(in_time_story_num) AS in_time_story_num,\n" +
            "\t\t\t SUM(out_time_story_num) AS out_time_story_num\n" +
            "FROM dash_board\n" +
            "WHERE project_id = #{projectId}")
    DashBoard selectByProject(Integer projectId);


}
