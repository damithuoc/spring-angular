package com.java.rigor.controllers;

import com.java.rigor.entity.Role;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.ErrorView;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.UserService;
import com.java.rigor.to.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 1/6/2016.
 */
@Controller
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginUser() {
        LOGGER.info("Entered loginUser()");
        return "login";
    }

    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public String loadUserHome() throws JavaRigorException {
        LOGGER.info("Entered loadUserHome()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User logInUser = userService.getUserByUsername(username);
        if (logInUser == null) {
            return "login";
        }
        return "userHome";
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.PUT)
    public ResponseEntity<ErrorView> saveUser(@RequestBody User user) throws JavaRigorException {
        LOGGER.info("Entered saveUser(" + user + ")");

        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (JavaRigorException jre) {

            return handleInternalServerError(jre);
        }

    }

    @RequestMapping(value = "/user/roles", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getAllUserRoles() throws JavaRigorException {
        LOGGER.info("Entered getUserRoles()");

        List<Role> roleList;
        try {
            roleList = userService.getAllUserRoles();

        } catch (JavaRigorException jre) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllActiveUsers() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveUsers()");

        Collection<User> userCollection;
        try {
            userCollection = userService.getAllActiveUsers();
        } catch (JavaRigorException jre) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userCollection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userCollection, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ErrorView> deleteUser(@PathVariable("id") Long id) throws JavaRigorException {
        LOGGER.info("Entered deleteUser(" + id + ")");

        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {
            try {
                userService.deleteUserByUserId(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }
        return handleUnAuthorizedError();
    }


    @RequestMapping(value = "/user/roles/edit/{username}", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> editUserRoles(@PathVariable("username") String username, @RequestBody List<Role> roleList) throws JavaRigorException {
        LOGGER.info("Entered editUserRoles(" + username + ")");

        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {
            try {
                userService.editUserRoles(roleList, username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }

        return handleUnAuthorizedError();
    }
}