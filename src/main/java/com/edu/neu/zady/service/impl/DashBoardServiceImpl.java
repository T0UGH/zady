package com.edu.neu.zady.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.mapper.DashBoardMapper;
import com.edu.neu.zady.pojo.DashBoard;
import com.edu.neu.zady.pojo.Sprint;
import com.edu.neu.zady.service.DashBoardService;
import com.edu.neu.zady.service.SprintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

@Transactional
@Service
public class DashBoardServiceImpl implements DashBoardService {

    private static final int DEFAULT_SLOT_NUM = 10;

    @Resource
    private DashBoardMapper dashBoardMapper;

    @Resource
    private SprintService sprintService;

    @Override
    public Integer insert(Integer sprintId) {

        Sprint sprint = sprintService.selectById(sprintId);

        if(sprint == null){
            throw new BadDataException("对应sprint + [" + sprintId + "]不存在");
        }

        int count = 0;

        for(int i = 1; i <= DEFAULT_SLOT_NUM; i ++){
            DashBoard dashBoard = new DashBoard();
            dashBoard.setSprintId(sprintId);
            dashBoard.setSlotId(i);
            dashBoard.setProjectId(sprint.getProjectId());
            count += dashBoardMapper.insert(dashBoard);
        }

        return count;

    }

    @Override
    public DashBoard selectBySprint(Integer sprintId) {

        DashBoard dashBoard = dashBoardMapper.selectBySprint(sprintId);

        if(dashBoard == null){
            throw new NotFoundException("对应sprint[" + sprintId + "]的dashBoard不存在");
        }

        return dashBoard;

    }

    @Override
    public DashBoard selectByProject(Integer projectId) {

        DashBoard dashBoard = dashBoardMapper.selectByProject(projectId);

        if(dashBoard == null){
            throw new NotFoundException("对应sprint[" + projectId + "]的dashBoard不存在");
        }

        return dashBoard;
    }



    @Override
    public Integer addBacklogNum(Integer sprintId) {

        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setBacklogNum(dashBoard.getBacklogNum() + 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer subBacklogNum(Integer sprintId) {

        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setBacklogNum(dashBoard.getBacklogNum() - 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer addFinishedBacklogNum(Integer sprintId) {

        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setFinishedBacklogNum(dashBoard.getFinishedBacklogNum() + 1);

        return dashBoardMapper.updateById(dashBoard);

    }

    @Override
    public Integer addStoryNum(Integer sprintId) {

        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setStoryNum(dashBoard.getStoryNum() + 1);

        return dashBoardMapper.updateById(dashBoard);

    }

    @Override
    public Integer subStoryNum(Integer sprintId) {

        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setStoryNum(dashBoard.getStoryNum() - 1);

        return dashBoardMapper.updateById(dashBoard);

    }

    @Override
    public Integer addInTimeStoryNum(Integer sprintId) {
        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setFinishedStoryNum(dashBoard.getFinishedStoryNum() + 1);
        dashBoard.setInTimeStoryNum(dashBoard.getInTimeStoryNum() + 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer addOutTimeStoryNum(Integer sprintId) {
        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setFinishedStoryNum(dashBoard.getFinishedStoryNum() + 1);
        dashBoard.setOutTimeStoryNum(dashBoard.getOutTimeStoryNum() + 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer addBugNum(Integer sprintId) {
        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setBugNum(dashBoard.getBugNum() + 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer subBugNum(Integer sprintId) {
        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setBugNum(dashBoard.getBugNum() - 1);

        return dashBoardMapper.updateById(dashBoard);
    }

    @Override
    public Integer addSolvedBugNum(Integer sprintId) {
        DashBoard dashBoard = randomDashBoard(sprintId);

        dashBoard.setSolvedBugNum(dashBoard.getSolvedBugNum() + 1);

        return dashBoardMapper.updateById(dashBoard);
    }



    private int randomSlot(){
        Random random = new Random();
        int rv = random.nextInt(DEFAULT_SLOT_NUM - 1) + 1;
        return rv;
    }

    private DashBoard randomDashBoard(Integer sprintId){
        int slotId = randomSlot();

        LambdaQueryWrapper<DashBoard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DashBoard::getSprintId, sprintId).eq(DashBoard::getSlotId, slotId);

        DashBoard dashBoard = dashBoardMapper.selectOne(lambdaQueryWrapper);

        if(dashBoard == null){
            throw new BadDataException("给定sprint[" + "]对应的dashboard不存在");
        }

        return dashBoard;
    }
}
