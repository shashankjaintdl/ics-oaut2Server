package com.ics.oauth2server.security;

import com.ics.oauth2server.common.entities.UserAccount;
//import com.ics.oauth2server.security.repository.UserAccountRepository;
import com.ics.oauth2server.helper.ConstantExtension;
import com.ics.oauth2server.useraccount.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Transactional
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTokenEnhancer.class);
    @Autowired
    private UserAccountRepository userAccountRepository;




    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LOGGER.info("Enhancing the OATH2 access token...");
        UserAccount user = (UserAccount) authentication.getPrincipal();
        List<UserAccount> userAccount = userAccountRepository.get(0L,user.getUsername(),null,true,null);
        if(userAccount.isEmpty()){
            throw new UsernameNotFoundException(ConstantExtension.USER_ACCOUNT_NOT_EXIST);
        }
        userAccount.get(0).setLoginCount(userAccount.get(0).getLoginCount()+1);
        userAccountRepository.saveOrUpdate(userAccount.get(0));
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
        info.put("id",userAccount.get(0).getId());
        info.put("userId",userAccount.get(0).getUserId());
        info.put("emailId", userAccount.get(0).getEmail());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }
}
