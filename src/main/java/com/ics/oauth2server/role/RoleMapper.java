package com.ics.oauth2server.role;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.HelperExtension;

public class RoleMapper {
    HelperExtension helperExtension = new HelperExtension();
    public Roles map(RoleRequest roleRequest){
        Roles entity = new Roles();
        return entity;
    }
}
