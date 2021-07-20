package com.ics.oauth2server.user.services;

import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.user.UserRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    APIResponse<User> save(UserRequest userRequest);
    APIResponse<User> delete(Long id,String username);
    APIResponse<User>  get(Long id, String username, String phoneNo, DatabaseHelper databaseHelper, HttpServletRequest httpServletRequest);
    APIResponse<User> update(Long id, String username,UserRequest userRequest);
}
