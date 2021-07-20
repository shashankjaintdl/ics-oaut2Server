package com.ics.oauth2server.user;

import lombok.Data;

public @Data class UserRequest {

    public String username;
    public String emailId;
    public String firstName;
    public String lastName;
    public String phoneNo;
    public String password;

}
