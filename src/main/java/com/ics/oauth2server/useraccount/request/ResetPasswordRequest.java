package com.ics.oauth2server.useraccount.request;

import lombok.Data;

public @Data class ResetPasswordRequest {
//    private String token;
    private String newPassword;
    private String confirmPassword;
}
