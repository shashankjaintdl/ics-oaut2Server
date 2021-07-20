package com.ics.oauth2server.user;

import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.helper.HelperExtension;

import java.util.Date;

public class UserMapper {

    HelperExtension helperExtension = new HelperExtension();
    public User map(UserRequest userRequest){
        User entity = new User();
        entity.setUsername(userRequest.getUsername());
        entity.setEmailId(userRequest.getEmailId());
        entity.setPhoneNo(userRequest.getPhoneNo());
        entity.setFirstName(userRequest.getFirstName());
        entity.setLastName(userRequest.getLastName());
        return entity;
    }
}
