package com.ics.oauth2server.security.token;

import com.ics.oauth2server.common.entities.SecureToken;
import com.ics.oauth2server.security.token.repository.SecureTokenRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
public class SecureTokenServiceImpl implements SecureTokenService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureTokenServiceImpl.class);
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${secure.token.expiredAfter}")
    private int tokenValidityInSecond;

    private final SecureTokenRepository secureTokenRepository;

    @Autowired
    public SecureTokenServiceImpl(SecureTokenRepository secureTokenRepository) {
        LOGGER.info("Secure Token Service token has started");
        this.secureTokenRepository = secureTokenRepository;
    }

    @Override
    public SecureToken createSecureToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()),US_ASCII);
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(tokenValidityInSecond));
        this.saveSecureToken(secureToken);
        return secureToken;
    }



    @Override
    public void saveSecureToken(SecureToken token) {
        secureTokenRepository.save(token);
    }

    @Override
    public SecureToken findByToken(String token) {
        SecureToken secureToken = secureTokenRepository.findByToken(token);
//        if(secureToken==null){
//            throw new IllegalStateException("Token either expired or does not exist");
//        }
        return secureToken;
    }

    @Override
    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
       secureTokenRepository.deleteByToken(token);
    }
}
