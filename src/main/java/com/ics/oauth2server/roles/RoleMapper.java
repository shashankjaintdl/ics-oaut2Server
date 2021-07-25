package com.ics.oauth2server.roles;

import com.ics.oauth2server.common.entities.Roles;

public class RoleMapper {
    public Roles map(RoleRequest roleRequest){
        Roles entity = new Roles();
//        if (roleRequest.getId()>0){
//            entity.setId(roleRequest.getId());
//        }
//        else{
//            entity.setId(0L);
//        }
        entity.setName(roleRequest.getName());
        return entity;
    }
}
