package com.ics.oauth2server.role.services;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Override
    public APIResponse<Roles> get(Long id, DatabaseHelper databaseHelper) {
        return null;
    }

}
