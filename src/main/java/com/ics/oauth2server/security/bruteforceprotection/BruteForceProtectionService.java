package com.ics.oauth2server.security.bruteforceprotection;

public interface BruteForceProtectionService {

     public void registerLoginFailure(String username);

     public void resetBruteForceCounter(String username);

     public boolean isBruteForceAttack(String username);

}
