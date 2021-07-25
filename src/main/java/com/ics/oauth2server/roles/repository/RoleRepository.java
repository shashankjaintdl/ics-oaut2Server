package com.ics.oauth2server.roles.repository;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.BaseRepository;

import java.util.List;

public interface RoleRepository extends BaseRepository<Roles> {
    Boolean isExist(Long id,String name);
    List<Roles> get(Long id, String name);
}
