package com.ics.oauth2server.role.services;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;

public interface RoleService {
    APIResponse<Roles> get(Long id, DatabaseHelper databaseHelper);
}
