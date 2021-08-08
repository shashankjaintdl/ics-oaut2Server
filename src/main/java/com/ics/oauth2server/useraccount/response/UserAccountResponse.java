package com.ics.oauth2server.useraccount.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class UserAccountResponse {
    private Long id;
    private String username;
    private String emailId;
}
