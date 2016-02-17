package com.java.rigor.dao;

import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 1/8/2016.
 */
public interface UserDao {

    void saveUser(User user);

    User getUserByUsername(String username);

    User getUserByUserId(Long userId);

    Collection<User> getAllActiveUsers();

    Boolean deleteUserByUserId(Long userId);

    Boolean saveUserRoles(List<Role> roleList, String username);

    Boolean validateUserName(String username);

    Boolean deleteUserRole(List<Role> roleList, String username);

    List<Role> getUserRoleListByUserName(String username);
}
