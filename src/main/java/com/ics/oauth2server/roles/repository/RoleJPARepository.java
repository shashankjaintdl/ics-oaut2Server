package com.ics.oauth2server.roles.repository;

import com.ics.oauth2server.common.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJPARepository extends JpaRepository<Roles,Long> {
}
