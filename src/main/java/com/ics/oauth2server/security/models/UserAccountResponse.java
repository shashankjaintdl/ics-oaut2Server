package com.ics.oauth2server.security.models;

import lombok.Data;

public @Data class UserAccountResponse {
    private String username;
    private Long id;
    private String emailId;
}
