package com.java.rigor.test.dao;

import com.java.rigor.constants.Constants;
import com.java.rigor.dao.UserDao;
import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 1/25/2016.
 */
public class UserDaoTest extends BaseTestClass {

    @Autowired
    private UserDao userDao;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    @Test
    public void testGetUserRoleListByUserName() {
        // test with null username.
        List<Role> roleList = userDao.getUserRoleListByUserName(null);
        Assert.assertFalse("Expected role list can't be null!", !roleList.isEmpty());

        // test with invalid username.
        roleList = userDao.getUserRoleListByUserName("ttttdddd");
        Assert.assertFalse("Expected role list can't be null!", !roleList.isEmpty());

        // test with valid username.
        roleList = userDao.getUserRoleListByUserName("sajith");
        Assert.assertFalse("Expected role list can be null!", roleList.isEmpty());
    }

    @Test
    public void testSaveUserRoles() {
        // test with role list and username null.
        Boolean userRoleSaveState = userDao.saveUserRoles(null, null);
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        // test with null role and empty username.
        userRoleSaveState = userDao.saveUserRoles(null, Constants.EMPTY_STRING);
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        // test with empty role list and null username.
        userRoleSaveState = userDao.saveUserRoles(new ArrayList<>(), null);
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        // test with both role list and username null.
        userRoleSaveState = userDao.saveUserRoles(new ArrayList<>(), Constants.EMPTY_STRING);
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        //test with invalid roles and invalid username
        List<Role> invalidRoleList = new ArrayList<>();
        Role invalidRole = new Role();
        invalidRole.setName("test");
        invalidRoleList.add(invalidRole);

        userRoleSaveState = userDao.saveUserRoles(invalidRoleList, "testddddddd");
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        // test with valid role and invalid username.
        List<Role> savedRoleList = new ArrayList<>();
        Role validRole = new Role();
        validRole.setName(UserRole.ROLE_USER.getRoleName());
        validRole.setCreatedDate(System.currentTimeMillis());
        validRole.setCreatedBy(username);
        savedRoleList.add(validRole);

        userRoleSaveState = userDao.saveUserRoles(savedRoleList, "testddddddd");
        Assert.assertTrue("User role save successful!", !userRoleSaveState);

        userRoleSaveState = userDao.saveUserRoles(savedRoleList, "testUser4");
        Assert.assertTrue("User role save unsuccessful!", userRoleSaveState);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Assume database user table not empty
        Collection<User> userCollection = userDao.getAllActiveUsers();
        Assert.assertTrue("Expected user collection is empty", !userCollection.isEmpty());
    }
}
