package com.ics.oauth2server.security.events;

import com.ics.oauth2server.security.bruteforceprotection.BruteForceProtectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final BruteForceProtectionService bruteForceProtectionService;

    @Autowired
    public AuthenticationFailureListener(BruteForceProtectionService bruteForceProtectionService) {
        this.bruteForceProtectionService = bruteForceProtectionService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        bruteForceProtectionService.registerLoginFailure(event.getAuthentication().getName());
    }

}
