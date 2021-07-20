package com.ics.oauth2server.security.bruteforceprotection;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.security.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service(value = "BruteForceProtectionService")
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService{

    public static final Logger LOGGER = LoggerFactory.getLogger(BruteForceProtectionServiceImpl.class);

    @Value("${jdj.security.failedlogin.count}")
    private int maxFailedLogins;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void registerLoginFailure(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount != null && userAccount.isAccountNonLocked()) {
            int failedCounter = userAccount.getFailedLoginAttempts();
            if (maxFailedLogins < failedCounter + 1) {

                userAccount.setAccountNonLocked(true); //disabling the account
            } else {
                //let's update the counter
                userAccount.setFailedLoginAttempts(failedCounter + 1);
            }
            userAccountRepository.save(userAccount);
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount != null) {
            LOGGER.info("User "+username + "logged in successfully in account at "+ LocalDateTime.now());
            userAccount.setFailedLoginAttempts(0);
            userAccount.setAccountNonLocked(false);
            userAccountRepository.save(userAccount);
        }
    }

    @Override
    public boolean isBruteForceAttack(String username) {
        UserAccount userAccount= userAccountRepository.findByUsername(username);
        if(userAccount!=null){
            return userAccount.getFailedLoginAttempts()>=3? true : false;
        }
        return false;
    }

}
