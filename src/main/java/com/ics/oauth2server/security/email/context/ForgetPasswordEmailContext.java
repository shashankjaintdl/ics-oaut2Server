package com.ics.oauth2server.security.email.context;

import com.ics.oauth2server.common.entities.UserAccount;
import org.springframework.web.util.UriComponentsBuilder;

public class ForgetPasswordEmailContext extends AbstractEmailContext{
    private String token;
    private String otp;

    @Override
    public <T> void init(T context) {
        UserAccount userAccount = (UserAccount) context;
        put("username",userAccount.getUsername());
        setTemplateLocation("reset-verify-email.ftl");
        setSubject("Reset your password");
        setFrom("jainshashank562@gmail.com");
        setTo("shashankjain536@gmail.com");
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/user-account/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

    public void setOTP(String otp){
        this.otp=otp;
        put("otp",otp);
    }
}
