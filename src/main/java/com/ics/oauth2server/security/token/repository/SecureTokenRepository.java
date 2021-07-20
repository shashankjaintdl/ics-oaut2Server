package com.ics.oauth2server.security.token.repository;

import com.ics.oauth2server.common.entities.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long> {

    public SecureToken findByToken(String token);

    public void deleteByToken(String token);

    SecureToken findByUserAccountId(Long id);

    List<SecureToken> findAllByUserAccountId(Long id);
}
