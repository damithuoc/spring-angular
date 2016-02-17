//package com.javaRigor.To;
//
//import UserDao;
//import Role;
//import User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//
///**
// * Created by work on 1/16/16.
// */
//
//@service("userDetailService")
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//public class JavaRigorUserDetailsService implements UserDetailsService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JavaRigorUserDetailsService.class);
//
//    @Autowired
//    private UserDao profileDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//
//        LOGGER.debug("Load user by username [{}]", username);
//        User user = profileDao.getUserByUsername(username);
//        if (user == null) {
//            String message = MessageFormat.format("Username [{0}] is not found", username);
//            LOGGER.error(message);
//            throw new UsernameNotFoundException(message);
//        }
//
//        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//
//        List<Role> roleList = user.getRoleList();
//        if (roleList != null) {
//            for (Role role : roleList) {
//                authorities.add(role);
//            }
//        }
//        return new AuthUser(user.getUsername(), user.getPassword(),
//                authorities,
//                !user.isAccountExpired(),
//                !user.isAccountLocked(),
//                !user.isCredentialsExpired(),
//                user.isEnabled());
//
//    }
//}


