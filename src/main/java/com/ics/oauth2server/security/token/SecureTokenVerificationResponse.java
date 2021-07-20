package com.ics.oauth2server.security.token;

import lombok.Data;

public @Data class SecureTokenVerificationResponse {
    private Long id;
    private String emailId;
    private String username;
}
