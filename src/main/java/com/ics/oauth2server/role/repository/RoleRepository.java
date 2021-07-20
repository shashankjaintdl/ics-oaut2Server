package com.ics.oauth2server.role.repository;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.BaseRepository;

public interface RoleRepository extends BaseRepository<Roles> {
    Boolean existByName(String name);
}
