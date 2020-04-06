package com.edu.neu.zady.service;

import com.edu.neu.zady.pojo.Story;

import java.util.List;

public interface StoryService {

    Story selectById(Integer storyId);

    List<Story> selectBySprintId(Integer sprintId);

    List<Story> selectByBacklogId(Integer backlogId);

    List<Story> selectBySprintIdAndStatus(Integer sprintId, String statusStr);

    Integer insert(Story story);

    Integer update(Story story);

    Integer delete(Integer storyId);

    Integer importStory(Integer storyId);

    Integer returnStory(Integer storyId);

    Integer developerReceive(Integer storyId, Integer userId);

    Integer developReturn(Integer storyId, Integer userId);

    Integer developFinish(Integer storyId, Integer userId, Integer useHours);

    Integer testReceive(Integer storyId, Integer userId);

    Integer testReturn(Integer storyId, Integer userId);

    Integer passTest(Integer storyId, Integer userId, Integer useHours);

    Integer notPassTest(Integer storyId, Integer userId, Integer useHours);

}
