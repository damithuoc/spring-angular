package com.java.rigor.service;

import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.exception.JavaRigorException;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */
public interface UserService {

    void saveUser(User user) throws JavaRigorException;

    User getUserByUserId(Long userId) throws JavaRigorException;

    Collection<User> getAllActiveUsers() throws JavaRigorException;

    Boolean deleteUserByUserId(Long userId) throws JavaRigorException;

    User getUserByUsername(String username) throws JavaRigorException;

    List<Role> getAllUserRoles() throws JavaRigorException;

    Boolean editUserRoles(List<Role> roleList, String username) throws JavaRigorException;
}
