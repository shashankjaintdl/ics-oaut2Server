package com.ics.oauth2server.otp;

import lombok.Data;

public @Data class OTPVerificationResponse {
    private long id;
    private String username;
    private String emailId;
    private String token;
}
