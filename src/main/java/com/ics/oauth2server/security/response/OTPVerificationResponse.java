package com.ics.oauth2server.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class OTPVerificationResponse {
    private long id;
    private String username;
    private String emailId;
    private Boolean isEnabled;
    private String token;
}
