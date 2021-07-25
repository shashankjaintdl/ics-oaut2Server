package com.ics.oauth2server.roles;


import com.ics.oauth2server.common.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public @Data class RoleRequest extends BaseRequest {
    private Long id;
    private String name;
    private List<Long> permissions;
}
