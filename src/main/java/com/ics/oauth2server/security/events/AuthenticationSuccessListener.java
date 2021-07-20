package com.ics.oauth2server.security.events;

import com.ics.oauth2server.security.bruteforceprotection.BruteForceProtectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessListener.class);
    private final BruteForceProtectionService bruteForceProtectionService;


    @Autowired
    public AuthenticationSuccessListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        bruteForceProtectionService.resetBruteForceCounter(username);
    }

}
