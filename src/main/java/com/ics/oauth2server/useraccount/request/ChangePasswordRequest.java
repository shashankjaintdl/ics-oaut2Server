package com.ics.oauth2server.useraccount.request;

import lombok.Data;

public @Data class ChangePasswordRequest {
    private Integer id;
    private String username;
    private String oldPassword;
    private String newPassword;
}
