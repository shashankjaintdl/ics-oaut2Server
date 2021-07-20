package com.ics.oauth2server.user.repository;

import com.ics.oauth2server.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Long> {
}
