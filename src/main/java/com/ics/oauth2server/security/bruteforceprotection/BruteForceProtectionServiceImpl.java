package com.ics.oauth2server.security.bruteforceprotection;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.useraccount.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service(value = "BruteForceProtectionService")
@Transactional
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService{

    public static final Logger LOGGER = LoggerFactory.getLogger(BruteForceProtectionServiceImpl.class);

    @Value("${jdj.security.failedlogin.count}")
    private int maxFailedLogins;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public BruteForceProtectionServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void registerLoginFailure(String username) {
        List<UserAccount> userAccount = userAccountRepository.get(0L,username,null,true,null);
        if (!userAccount.isEmpty() && userAccount.get(0).isAccountNonLocked()) {
            int failedCounter = userAccount.get(0).getFailedLoginAttempts();
            if (maxFailedLogins < failedCounter + 1) {

                userAccount.get(0).setAccountNonLocked(true); //disabling the account
            } else {
                //let's update the counter
                userAccount.get(0).setFailedLoginAttempts(failedCounter + 1);
            }
            userAccountRepository.saveOrUpdate(userAccount.get(0));
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        List<UserAccount> userAccount = userAccountRepository.get(0L,username,null,true,null);

        if (!userAccount.isEmpty()) {
            LOGGER.info("User "+username + "logged in successfully in account at "+ LocalDateTime.now());
            userAccount.get(0).setFailedLoginAttempts(0);
            userAccount.get(0).setAccountNonLocked(false);
            userAccountRepository.saveOrUpdate(userAccount.get(0));
        }
    }

    @Override
    public boolean isBruteForceAttack(String username) {
        List<UserAccount> userAccount = userAccountRepository.get(0L,username,null,true,null);
        if(!userAccount.isEmpty()){
            return userAccount.get(0).getFailedLoginAttempts()>=3? true : false;
        }
        return false;
    }

}
