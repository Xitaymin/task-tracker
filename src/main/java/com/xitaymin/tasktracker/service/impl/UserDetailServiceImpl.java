package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    public static final String NOT_FOUND_USER_BY_EMAIL = "User with email %s not found.";
    private final UserDAO userDAO;

    public UserDetailServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(NOT_FOUND_USER_BY_EMAIL, username));
        }

        //todo add password field
//        return new UserCredentials(user.getId(),user.getEmail(),user.getPassword,user.getRoles());
        return null;
    }
}
