package com.ics.oauth2server.security.email.context;


import com.ics.oauth2server.common.entities.UserAccount;

public class NewRegistrationUsernameAndPasswordEmailContext extends AbstractEmailContext{

    private String username;
    private String password;

    @Override
    public <T> void init(T context) {
        UserAccount userAccount = (UserAccount) context;
        put("username",userAccount.getUsername());
        setTemplateLocation("new-user-registration.ftl");
        setSubject("Reset your password");
        setFrom("jainshashank562@gmail.com");
        setTo("shashankjain536@gmail.com");
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
        put("password",password);
    }

}
