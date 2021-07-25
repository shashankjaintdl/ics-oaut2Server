package com.ics.oauth2server.roles.repository;

import com.ics.oauth2server.common.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionJPARepository extends JpaRepository<Permission,Long> {

}
