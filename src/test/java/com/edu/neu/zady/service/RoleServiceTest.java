package com.edu.neu.zady.service;

import com.edu.neu.zady.ZadyApplicationTests;
import com.edu.neu.zady.exception.BadDataException;
import com.edu.neu.zady.pojo.Project;
import com.edu.neu.zady.pojo.Role;
import com.edu.neu.zady.pojo.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest extends ZadyApplicationTests {

    @Autowired
    RoleService roleService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void inviteUserAndJoin(){
        Integer projectId = 3;
        Integer userId = 1;
        int affectRow = roleService.inviteUser(projectId, userId);
        assertEquals(1, affectRow);
        affectRow = roleService.joinProject(projectId, userId);
        assertEquals(1, affectRow);
    }

    @Test
    void inviteUser2(){
        try{
            Integer projectId = 3;
            Integer userId = 2;
            roleService.inviteUser(projectId, userId);
            fail();
        }catch (BadDataException e){
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void deleteUser(){
        int affectRow = roleService.deleteUser(1, 9);
        assertEquals(1, affectRow);
    }

    @Test
    void updateByPIdAndUId(){
        Integer projectId = 3;
        Integer userId = 1;
        String role = "developer";
        int affectRow = roleService.updateByPIdAndUId(projectId, userId, role);
        assertEquals(1, affectRow);
        Role dbRole = roleService.selectByPIdAndUId(projectId, userId);
        assertEquals(dbRole.getRole(), role);
    }

    @Test
    void selectUsersByPId(){
        Integer projectId = 3;
        List<User> userList= roleService.selectUsersByPId(projectId);
        assertNotEquals(0, userList.size());
    }

    @Test
    void selectInvitesByPId(){
        Integer projectId = 1;
        List<User> userList= roleService.selectInviteUsersByPId(projectId);
        assertEquals(0, userList.size());
    }

    @Test
    void selectProjectsByUId(){
        Integer userId = 1;
        List<Project> projectList= roleService.selectProjectsByUId(userId);
        assertNotEquals(0, projectList.size());
    }
}