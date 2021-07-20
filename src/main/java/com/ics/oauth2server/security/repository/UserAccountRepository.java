package com.ics.oauth2server.security.repository;

import com.ics.oauth2server.common.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    UserAccount findByUsername(String username);
    Optional<UserAccount> findByUserId(Long userId);
    List<UserAccount> findAllByUsername(String username);
    List<UserAccount> findAllByIdOrUsername(Long id,String username);
}
