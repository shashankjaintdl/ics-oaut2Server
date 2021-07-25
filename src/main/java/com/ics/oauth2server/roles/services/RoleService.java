package com.ics.oauth2server.roles.services;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.roles.RoleRequest;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {
    APIResponse<Roles> save(RoleRequest roleRequest, HttpServletRequest httpServletRequest);
    APIResponse<Roles> update(Long id, String name, RoleRequest roleRequest, HttpServletRequest httpServletRequest);
    APIResponse<Roles> assignPermissions(Long id, String name, RoleRequest request, HttpServletRequest httpServletRequest);
//    APIResponse<Roles> get(Long id, DatabaseHelper databaseHelper);
}
