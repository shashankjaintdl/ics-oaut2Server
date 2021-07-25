package com.ics.oauth2server.roles;

import lombok.Data;

import java.util.List;

public @Data class PermissionRequest {
    private List<Long> id;
}
