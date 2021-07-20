package com.ics.oauth2server.security.token;

import com.ics.oauth2server.common.entities.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(final SecureToken token);

    SecureToken findByToken(final String token);

    void removeToken(final SecureToken token);

    void removeTokenByToken(final String token);
}
