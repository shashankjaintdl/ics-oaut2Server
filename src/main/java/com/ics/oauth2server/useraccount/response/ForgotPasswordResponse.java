package com.ics.oauth2server.useraccount.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class ForgotPasswordResponse {
    private Long id;
    private String username;
}
