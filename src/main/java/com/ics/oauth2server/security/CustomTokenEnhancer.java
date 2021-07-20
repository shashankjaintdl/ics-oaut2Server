package com.ics.oauth2server.security;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.security.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenEnhancer.class);
    @Autowired
    private UserAccountRepository userAccountRepository;




    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LOGGER.info("Enhancing the OATH2 access token...");
        UserAccount user = (UserAccount) authentication.getPrincipal();
        UserAccount userAccount = userAccountRepository.findByUsername(user.getUsername());
        userAccount.setLoginCount(userAccount.getLoginCount()+1);
        userAccountRepository.save(userAccount);
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

        info.put("id",userAccount.getId());
        info.put("userId",userAccount.getUserId());
        info.put("emailId", user.getEmail());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        return super.enhance(customAccessToken, authentication);
    }
}
