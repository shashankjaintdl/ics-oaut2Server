package com.ics.oauth2server.security.services;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.security.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service(value = "userDetailsService")
public class CustomUserDetailServices implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailServices.class);
    private final UserAccountRepository userAccountRepository;
//    private final AdditionalRoleRepository additionalRoleRepository;

    @Autowired
    public CustomUserDetailServices(UserAccountRepository userAccountRepository) {
        LOGGER.info("Custom User Detail Service has been started");
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccount userAccount= userAccountRepository.findByUsername(s);


        if(userAccount==null) {
            LOGGER.debug("Invalid Username and password");
            throw new BadCredentialsException("Invalid Username and password");
        }

        if(!userAccount.isFlag()){
            LOGGER.debug("You are allowed to login, kindly contact to our help desk");
            throw new IllegalArgumentException("You are allowed to login, kindly contact to our help desk");
        }

        if(!userAccount.isAccountVerified()){
            LOGGER.debug("Email is not verified");
            throw new IllegalArgumentException("Email is not verified");
        }


        String role=userAccount.getRoles().get(0).getName();

        new AccountStatusUserDetailsChecker().check(userAccount);

        // TODO: Getting the additional permission
//        Optional<List<AdditionalRoles>> additionalRoles = additionalRoleRepository.findAllByUserAccountId(userAccount.getId());

        Set<GrantedAuthority> authorities = new HashSet<>();
        userAccount.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
            r.getPermissions().forEach(p -> {
                authorities.add(new SimpleGrantedAuthority(p.getName()));
            });

        });

        //TODO: Need to add the dynamic permission.
//        if(additionalRoles.isPresent()){
//            additionalRoles.get().forEach(ad->{
//                authorities.add(new SimpleGrantedAuthority(ad.getName()));
//            });
//        }

        userAccount.setAuthorities(authorities);
        return userAccount;
    }


}
