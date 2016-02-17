package com.java.rigor.service.impl;

import com.java.rigor.dao.UserDao;
import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.UserService;
import com.java.rigor.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by sanandasena on 1/11/2016.
 */
@Service("userService")
@Configurable
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * save a user account.
     *
     * @param user
     * @throws JavaRigorException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public void saveUser(User user) throws JavaRigorException {
        LOGGER.info("Entered saveUser()");

        if (user == null) {
            String errorMessage = "The user object to create a user is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            String errorMessage = "Username null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            String errorMessage = "Password null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }
        if (user.getRoleList() == null || user.getRoleList().isEmpty()) {
            String errorMessage = "User roles null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        List<Role> roleList = user.getRoleList();

        if (!validateUserRoles(roleList)) {
            String errorMessage = "Invalid user roles!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

//         encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(System.currentTimeMillis());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         get the login user
        String username = auth.getName();
        user.setCreatedBy(username);

        try {

            userDao.saveUser(user);
            LOGGER.info("saved new user!");

        } catch (DataAccessException dae) {

            String errorMessage = "database error occurred while creating  user!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }

    }

    /**
     * get a user by using username.
     *
     * @param username
     * @return
     * @throws JavaRigorException
     */
    public User getUserByUsername(final String username) throws JavaRigorException {
        LOGGER.info("Entered getUserByUsername(" + username + ")");

        if (username == null || username.isEmpty()) {
            String errorMessage = "Username  null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);

        }
        try {
            return userDao.getUserByUsername(username);
        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while fetching  user by username !";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * @param userId
     * @return
     * @throws JavaRigorException
     */
    public User getUserByUserId(Long userId) throws JavaRigorException {
        LOGGER.info("Entered getUserByUserId(" + userId + ")");

        if (userId == null) {
            String errorMessage = "User id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        try {
            return userDao.getUserByUserId(userId);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while fetching a user!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get all user roles.
     *
     * @return
     * @throws JavaRigorException
     */
    public List<Role> getAllUserRoles() throws JavaRigorException {
        LOGGER.info("Entered getAllUserRoles()");
        List<UserRole> userRoleList = new ArrayList<>(EnumSet.allOf(UserRole.class));

        List<Role> roleList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            Role role = new Role();
            role.setName(userRole.getRoleName());
            roleList.add(role);
        }
        return roleList;
    }

    /**
     * get all active users.
     *
     * @return
     * @throws JavaRigorException
     */
    public Collection<User> getAllActiveUsers() throws JavaRigorException {
        LOGGER.info("Enterd getAllActiveUsers()");

        try {
            return userDao.getAllActiveUsers();
        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while fetching  all users!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }

    }

    /**
     * delete user by using user id.
     *
     * @param userId
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean deleteUserByUserId(final Long userId) throws JavaRigorException {
        LOGGER.info("Entered deleteUserByUserId(" + userId + ")");

        if (userId == null) {
            String errorMessage = "User id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        try {

            return userDao.deleteUserByUserId(userId);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while deleting a user!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * edit user roles using roles and username.
     *
     * @param roleList
     * @param username
     * @return
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean editUserRoles(List<Role> roleList, final String username) throws JavaRigorException {
        LOGGER.info("Entered editUserRoles(" + roleList + ", " + username + ")");

        if (username == null || username.isEmpty()) {
            String errorMessage = "Username is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (roleList == null || roleList.isEmpty()) {
            String errorMessage = "User role list is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (!validateUserRoles(roleList)) {
            String errorMessage = "Invalid user role list !";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (!userDao.validateUserName(username)) {
            String errorMessage = "Invalid username !";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // get user role list by username
        String loginUser = auth.getName();

        List<Role> userRoleListDb = userDao.getUserRoleListByUserName(username);
        Set<Role> saveUserRoleSet = new HashSet<>();
        Set<Role> deleteRoleSet = new HashSet<>();

        if (userRoleListDb == null || userRoleListDb.isEmpty()) {
            // check and save user roles
            roleList.stream().filter(saveRole -> Boolean.TRUE.equals(saveRole.getIsActive())).forEach(saveRole -> {
                saveRole.setCreatedBy(loginUser);
                saveRole.setCreatedDate(System.currentTimeMillis());
                saveUserRoleSet.add(saveRole);

            });
        } else {
            //  find save and delete roles
            for (Role dbRole : userRoleListDb) {
                for (Role frontEndRole : roleList) {
                    if (dbRole.getName().equals(frontEndRole.getName()) && Boolean.FALSE.equals(frontEndRole.getIsActive())) {
                        deleteRoleSet.add(dbRole);

                    } else if (!(userRoleListDb.contains(frontEndRole)) && Boolean.TRUE.equals(frontEndRole.getIsActive())) {
                        frontEndRole.setCreatedBy(loginUser);
                        frontEndRole.setCreatedDate(System.currentTimeMillis());
                        saveUserRoleSet.add(frontEndRole);
                    }
                }
            }
        }
        List<Role> saveNewRoleList = new ArrayList<>(saveUserRoleSet);
        List<Role> deleteRoleList = new ArrayList<>(deleteRoleSet);

        try {
            Boolean editStatus = Boolean.FALSE;

            if (!saveNewRoleList.isEmpty()) {
                Boolean saveStatus = userDao.saveUserRoles(saveNewRoleList, username);
                if (saveStatus) {
                    editStatus = Boolean.TRUE;
                }
            }

            if (!deleteRoleList.isEmpty()) {
                Boolean deleteStatus = userDao.deleteUserRole(deleteRoleList, username);
                if (deleteStatus) {
                    editStatus = Boolean.TRUE;
                }
            }

            if (saveNewRoleList.isEmpty() && deleteRoleList.isEmpty()) {
                editStatus = Boolean.FALSE;
            }

            LOGGER.info("User roles edit successfully!");
            return editStatus;

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while editing user roles!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }

    }

    // validate user roles
    private Boolean validateUserRoles(List<Role> roleList) {
        LOGGER.info("Entered validateUserRoles(" + roleList + ")");

        List<UserRole> systemRoleList = new ArrayList<>(EnumSet.allOf(UserRole.class));

        List<String> userRoleStrs = new ArrayList<>();

        for (UserRole userRole : systemRoleList) {
            userRoleStrs.add(userRole.getRoleName());
        }

        if (roleList != null && !roleList.isEmpty()) {
            Boolean isRole = Boolean.FALSE;

            for (Role role : roleList) {
                if (role.getName() != null && !role.getName().isEmpty()) {
                    if (!userRoleStrs.isEmpty()) {
                        if (!userRoleStrs.contains(role.getName())) {
                            break;
                        }
                    }
                } else {
                    break;
                }
                isRole = Boolean.TRUE;
            }

            if (isRole) {
                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;
    }


}
