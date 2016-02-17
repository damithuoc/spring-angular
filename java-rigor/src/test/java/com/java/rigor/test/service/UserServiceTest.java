package com.java.rigor.test.service;

import com.java.rigor.constants.Constants;
import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.UserService;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 1/20/2016.
 */
public class UserServiceTest extends BaseTestClass {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() throws Exception {
        //test with null user
        try {
            userService.saveUser(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty uesr;
        try {
            userService.saveUser(new User());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with username empty
        User invalidUser = new User();
        invalidUser.setUsername(Constants.EMPTY_STRING);
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with pass word null
        invalidUser.setUsername("invalidUser");
        invalidUser.setPassword(null);
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty password
        invalidUser.setPassword(Constants.EMPTY_STRING);
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with user roles empty list
        invalidUser.setPassword("testpw");
        invalidUser.setRoleList(new ArrayList<>());
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid role
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        invalidUser.setRoleList(roleList);
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty role name
        Role userRole = new Role();
        userRole.setName(Constants.EMPTY_STRING);
        roleList.add(userRole);
        invalidUser.setRoleList(roleList);
        try {
            userService.saveUser(invalidUser);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

//         test with valid user details
        User validUser = new User();
        validUser.setUsername("testUser5");
        validUser.setPassword("test123");

        List<Role> userRoleList = new ArrayList<>();
        Role role1 = new Role();
        role1.setName(UserRole.ROLE_ADMIN.getRoleName());
        userRoleList.add(role1);

        Role role2 = new Role();
        role2.setName(UserRole.ROLE_USER.getRoleName());
        userRoleList.add(role2);

        validUser.setRoleList(userRoleList);
        userService.saveUser(validUser);
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        // test with null username
        try {
            userService.getUserByUsername(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty username

        try {
            userService.getUserByUsername(Constants.EMPTY_STRING);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }
        // test with invalid username
        User fetchedUser = userService.getUserByUsername("nnnnnn");
        Assert.assertFalse("Fetched user can't be null!", fetchedUser != null);

        // test with valid username
        fetchedUser = userService.getUserByUsername("sajith");
        Assert.assertFalse("Fetched user can be null! ", fetchedUser == null);
    }

    @Test
    public void testGetAllActiveUser() throws Exception {
        Collection<User> userList = userService.getAllActiveUsers();
        Assert.assertNotNull(userList);
    }

    @Test
    public void testGetUserByUserId() throws Exception {
        //test with null user id
        try {
            userService.getUserByUserId(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid user id
        User fetchUser = userService.getUserByUserId(76677887L);
        Assert.assertFalse("Expected fetch user can't be null!", fetchUser != null);

        fetchUser = userService.getUserByUserId(1453440484566580L);
        Assert.assertFalse("Expected fetch user can be null!", fetchUser == null);
    }

    @Test
    public void testDeleteUserByUserId() throws Exception {
        //test with null user id
        try {
            userService.deleteUserByUserId(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid user id
        Boolean deleteState = userService.deleteUserByUserId(343433L);
        Thread.sleep(100);
        Assert.assertFalse("Expected user deleted!", deleteState);
        // test with valid user id
        deleteState = userService.deleteUserByUserId(1453440835656828L);
        Thread.sleep(100);
        Assert.assertTrue("Expected user not deleted!", deleteState);

    }

    @Test
    public void testEditUserRoles() throws Exception {
        // test with null role list and username
        try {
            userService.editUserRoles(null, null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty role list and null username
        try {
            userService.editUserRoles(new ArrayList<>(), null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid role list and invalid username

        List<Role> invalidRoleList = new ArrayList<>();
        Role invalidRole = new Role();
        invalidRoleList.add(invalidRole);
        String invalidUsername = "testttt";
        try {
            userService.editUserRoles(invalidRoleList, invalidUsername);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid role name
        invalidRole.setName("test");
        invalidRoleList.add(invalidRole);
        try {
            userService.editUserRoles(invalidRoleList, invalidUsername);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid role and invalid username
        List<Role> validRoleList = new ArrayList<>();
        Role validRole = new Role();
        validRole.setName(UserRole.ROLE_USER.getRoleName());
        validRoleList.add(validRole);

        try {
            userService.editUserRoles(validRoleList, invalidUsername);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid role, isActive null and valid username
        String validUserName = "testUser4";
        Boolean editStatus = userService.editUserRoles(validRoleList, validUserName);
        Assert.assertTrue("User roles edit successful! ", !editStatus);

        // test with valid role, isActive false and valid username
        validRole.setIsActive(Boolean.FALSE);
        editStatus = userService.editUserRoles(validRoleList, validUserName);
        Assert.assertTrue("User roles edit successful! ", !editStatus);


        // test with valid roles
        List<Role> validRoleList2 = new ArrayList<>();
        Role validRole2 = new Role();
        validRole2.setName(UserRole.ROLE_USER.getRoleName());
        validRole2.setIsActive(Boolean.TRUE);
        validRoleList2.add(validRole2);
        editStatus = userService.editUserRoles(validRoleList2, validUserName);
        Assert.assertTrue("User roles edit unsuccessful! ", editStatus);

        // test with valid new user role and role isActive false and valid username
        List<Role> validRoleList3 = new ArrayList<>();
        Role validRole3 = new Role();
        validRole3.setName(UserRole.ROLE_ADMIN.getRoleName());
        validRole3.setIsActive(Boolean.FALSE);
        validRoleList3.add(validRole3);

        Role validRole4 = new Role();
        validRole4.setName(UserRole.ROLE_USER.getRoleName());
        validRole4.setIsActive(Boolean.TRUE);
        validRoleList3.add(validRole4);

        editStatus = userService.editUserRoles(validRoleList3, validUserName);
        Assert.assertTrue("User roles edit successful! ", !editStatus);// since before execution added a role

        // test with valid new user role and role isActive true
        // previous role isActive false
        // valid username
        List<Role> validRoleList4 = new ArrayList<>();
        Role validRole5 = new Role();
        validRole5.setName(UserRole.ROLE_ADMIN.getRoleName());
        validRole5.setIsActive(Boolean.TRUE);
        validRoleList4.add(validRole5);

        Role validRole6 = new Role();
        validRole6.setName(UserRole.ROLE_USER.getRoleName());
        validRole6.setIsActive(Boolean.FALSE);
        validRoleList4.add(validRole6);

        editStatus = userService.editUserRoles(validRoleList4, validUserName);
        Assert.assertTrue("User roles edit unsuccessful! ", editStatus);

    }

}
