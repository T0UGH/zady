package com.edu.neu.zady;

import com.edu.neu.zady.annotation.Auth;
import com.edu.neu.zady.exception.NotFoundException;
import com.edu.neu.zady.pojo.Bornout;
import com.edu.neu.zady.pojo.DashBoard;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.service.BornoutService;
import com.edu.neu.zady.service.DashBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StatisticController {

    @Resource
    DashBoardService dashBoardService;

    @Resource
    BornoutService bornoutService;

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/project/{projectId}/dashBoard")
    public DashBoard getDashBoardByProject(@PathVariable Integer projectId) {

        DashBoard dashBoard = dashBoardService.selectByProject(projectId);

        if(dashBoard == null){
            throw new NotFoundException("给定project[" + projectId +"]对应dashboard未找到");
        }

        return dashBoard;

    }

    @Auth(sameProject = true, role = {Role.RoleEnum.master, Role.RoleEnum.owner, Role.RoleEnum.developer, Role.RoleEnum.tester})
    @GetMapping("/sprint/{sprintId}/dashBoard")
    public DashBoard getDashBoardBySprint(@PathVariable Integer sprintId) {

        DashBoard dashBoard = dashBoardService.selectBySprint(sprintId);

        if(dashBoard == null){
            throw new NotFoundException("给定sprint[" + sprintId +"]对应dashboard未找到");
        }

        return dashBoard;

    }
    @GetMapping("/sprint/{sprintId}/bornout")
    public List<Bornout> getBornoutBySprint(@PathVariable Integer sprintId) {

        List<Bornout> bornoutList = bornoutService.selectBySprint(sprintId);

        if(bornoutList == null){
            throw new NotFoundException("给定sprint[" + sprintId +"]对应bornout未找到");
        }

        return bornoutList;

    }
}
