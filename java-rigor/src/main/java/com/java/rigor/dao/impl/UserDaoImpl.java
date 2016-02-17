package com.java.rigor.dao.impl;

import com.java.rigor.constants.DaoConstants;
import com.java.rigor.dao.UserDao;
import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanandasena on 1/8/2016.
 */
@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String SAVE_USER = "INSERT INTO USER(ID, USERNAME, PASSWORD, IS_ACTIVE, CREATED_BY, CREATED_DATE) VALUES("
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_USERNAME + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_PASSWORD + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_IS_ACTIVE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_BY + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_DATE + ")";

    private static final String SAVE_USER_ROLE = "INSERT INTO USER_ROLE(ID, USERNAME, ROLE, CREATED_BY, CREATED_DATE, IS_ACTIVE ) VALUES("
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_USERNAME + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_ROLE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_BY + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_DATE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_IS_ACTIVE + ")";

    private static final String GET_USER_BY_USERNAME = "SELECT U.USERNAME, U.PASSWORD, U.IS_ACTIVE, UR.ROLE FROM USER_ROLE UR, USER U" +
            " WHERE U.IS_ACTIVE = TRUE AND " +
            " U.USERNAME = " + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_USERNAME;

    private static final String GET_ALL_USERS = "SELECT U.ID, U.USERNAME, U.IS_ACTIVE, UR.ROLE FROM USER_ROLE UR RIGHT JOIN USER U "
            + " ON (U.USERNAME = UR.USERNAME) WHERE U.IS_ACTIVE = TRUE ";

    private static final String DISABLE_USER_BY_USER_ID = "UPDATE USER SET IS_ACTIVE = FALSE " +
            " WHERE ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String GET_ACTIVE_USER_BY_USER_ID = "SELECT * FROM USER "
            + " WHERE ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID
            + " AND IS_ACTIVE = TRUE";

    private static final String GET_ALL_USER_IDS = "SELECT ID FROM USER";

    private static final String DELETE_USER_ROLES = " DELETE FROM USER_ROLE WHERE"
            + " USERNAME = " + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_USERNAME + " AND "
            + " ROLE = " + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_ROLE;


    private static final String GET_USER_ROLES_BY_USERNAME = "SELECT ROLE FROM USER_ROLE WHERE IS_ACTIVE = TRUE AND "
            + " USERNAME = " + DaoConstants.General.COLON + DaoConstants.UserDao.PARAM_USERNAME;

    private static final String GET_ALL_ACTIVE_USERS_USERNAME = "SELECT USERNAME FROM USER WHERE IS_ACTIVE = TRUE";

    /**
     * save user
     *
     * @param user
     */
    public void saveUser(User user) {

        LOGGER.info("Entered saveUser(" + user + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Long id = IdGenerator.generateId();
        params.addValue(DaoConstants.General.PARAM_ID, id);
        params.addValue(DaoConstants.UserDao.PARAM_USERNAME, user.getUsername());
        params.addValue(DaoConstants.UserDao.PARAM_PASSWORD, user.getPassword());
        params.addValue(DaoConstants.General.PARAM_IS_ACTIVE, Boolean.TRUE);
        params.addValue(DaoConstants.General.PARAM_CREATED_BY, user.getCreatedBy());
        params.addValue(DaoConstants.General.PARAM_CREATED_DATE, user.getCreatedDate());

        namedParameterJdbcTemplate.update(SAVE_USER, params);

        if (user.getRoleList() != null && !user.getRoleList().isEmpty()) {

            for (Role role : user.getRoleList()) {
                role.setCreatedBy(user.getCreatedBy());
                role.setCreatedDate(user.getCreatedDate());
            }

            saveUserRoles(user.getRoleList(), user.getUsername());
        }

    }

    /**
     * save user role for existing user.
     *
     * @param roleList
     * @param username
     */
    public Boolean saveUserRoles(List<Role> roleList, String username) {
        LOGGER.info("Entered saveUserRoles(" + roleList + "," + username + ")");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Integer runCount = roleList.size();

        for (Role role : roleList) {
            // generate role id.
            Long id = IdGenerator.generateId();
            params.addValue(DaoConstants.General.PARAM_ID, id);
            params.addValue(DaoConstants.UserDao.PARAM_USERNAME, username);
            params.addValue(DaoConstants.UserDao.PARAM_ROLE, role.getName());
            params.addValue(DaoConstants.General.PARAM_IS_ACTIVE, Boolean.TRUE);
            params.addValue(DaoConstants.General.PARAM_CREATED_BY, role.getCreatedBy());
            params.addValue(DaoConstants.General.PARAM_CREATED_DATE, role.getCreatedDate());

            namedParameterJdbcTemplate.update(SAVE_USER_ROLE, params);
            LOGGER.info("Saved user role :" + role.getName());

            runCount--;

        }
        if (runCount.equals(0)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * validate username.
     *
     * @param username
     * @return
     */
    public Boolean validateUserName(final String username) {
        LOGGER.info("Entered validateUserName(" + username + ")");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        if (username != null && !username.isEmpty()) {
            List<String> userNameList = namedParameterJdbcTemplate.query(GET_ALL_ACTIVE_USERS_USERNAME, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString(DaoConstants.UserDao.USERNAME);
                }
            });

            if (!userNameList.isEmpty() && userNameList.contains(username)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * get a user by username.
     *
     * @param username
     * @return
     */
    public User getUserByUsername(final String username) {

        LOGGER.info("Entered getUserByUsername(" + username + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.UserDao.PARAM_USERNAME, username);

        final Map<String, User> userMap = new HashMap<>();

        namedParameterJdbcTemplate.query(GET_USER_BY_USERNAME, params, getUserByUsernameRowMapper(userMap));

        if (userMap.isEmpty()) {
            return null;
        }
        return userMap.get(username);
    }

    private RowMapper<User> getUserByUsernameRowMapper(final Map<String, User> userMap) {

        return (resultSet, i) -> {
            String username = resultSet.getString(DaoConstants.UserDao.USERNAME);
            User user = userMap.get(username);

            if (user == null) {
                user = new User();
                user.setPassword(resultSet.getString(DaoConstants.UserDao.PASSWORD));
                user.setIsActive(resultSet.getBoolean(DaoConstants.General.IS_ACTIVE));
            }

            Role role = new Role();
            role.setName(resultSet.getString(DaoConstants.UserDao.ROLE));
            user.addRole(role);
            userMap.put(username, user);

            return user;

        };
    }

    /**
     * get all active users.
     *
     * @return
     */
    public Collection<User> getAllActiveUsers() {
        LOGGER.info("Entered getAllActiveUsers()");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        final Map<String, User> userMap = new HashMap<String, User>();

        namedParameterJdbcTemplate.query(GET_ALL_USERS, getAllActiveUsersRowMapper(userMap));

        return userMap.values();
    }

    private RowMapper<User> getAllActiveUsersRowMapper(final Map<String, User> userMap) {
        return (resultSet, i) -> {
            String username = resultSet.getString(DaoConstants.UserDao.USERNAME);
            User user = userMap.get(username);

            if (user == null) {
                user = new User();
                user.setUsername(username);
                user.setId(resultSet.getLong(DaoConstants.General.ID));
                user.setIsActive(resultSet.getBoolean(DaoConstants.General.IS_ACTIVE));
            }

            Role role = new Role();
            role.setName(resultSet.getString(DaoConstants.UserDao.ROLE));
            role.setIsActive(Boolean.TRUE);
            user.addRole(role);
            userMap.put(username, user);

            return user;
        };
    }

    /**
     * get a user by using user id.
     *
     * @param userId
     * @return
     */
    public User getUserByUserId(final Long userId) {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, userId);

        final Map<Long, User> userMap = new HashMap<>();

        namedParameterJdbcTemplate.query(GET_ACTIVE_USER_BY_USER_ID, params, (resultSet, i) -> {

            Long id = resultSet.getLong(DaoConstants.General.ID);
            User user = userMap.get(id);

            if (user == null) {
                user = new User();
                user.setId(id);
                user.setUsername(resultSet.getString(DaoConstants.UserDao.USERNAME));
            }
            userMap.put(id, user);

            return user;
        });

        return userMap.get(userId);
    }


    /**
     * disable user by user id.
     *
     * @param userId
     * @return
     */
    public Boolean deleteUserByUserId(final Long userId) {
        LOGGER.info("Enterd deleteUserByUserId(" + userId + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, userId);

        namedParameterJdbcTemplate.update(DISABLE_USER_BY_USER_ID, params);

        List<Long> userIdList = namedParameterJdbcTemplate.query(GET_ALL_USER_IDS, (resultSet, i) -> {
            return resultSet.getLong(DaoConstants.General.ID);
        });

        if (userIdList != null && !userIdList.isEmpty()) {
            if (!userIdList.contains(userId)) {
                return Boolean.FALSE;
            } else {
                User fetchUser = getUserByUserId(userId);
                if (fetchUser == null) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * delete user roles.
     *
     * @param roleList
     * @param username
     * @return
     */
    public Boolean deleteUserRole(List<Role> roleList, String username) {
        LOGGER.info("Entered deleteUserRole(" + username + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Integer runCount = roleList.size();

        for (Role role : roleList) {
            params.addValue(DaoConstants.UserDao.PARAM_USERNAME, username);
            params.addValue(DaoConstants.UserDao.PARAM_ROLE, role.getName());
            namedParameterJdbcTemplate.update(DELETE_USER_ROLES, params);

            runCount--;
        }

        if (runCount.equals(0)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public List<Role> getUserRoleListByUserName(final String username) {
        LOGGER.info("Entered getUserRoleListByUserName(" + username + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.UserDao.PARAM_USERNAME, username);

        return namedParameterJdbcTemplate.query(GET_USER_ROLES_BY_USERNAME, params, (resultSet, i) -> {
            Role role = new Role();
            role.setName(resultSet.getString(DaoConstants.UserDao.ROLE));
            role.setIsActive(Boolean.TRUE);

            return role;
        });
    }
}
