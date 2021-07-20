package com.ics.oauth2server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderConfiguration.class);
    @Bean
    public PasswordEncoder passwordEncoder(){
        LOGGER.info("Password Started......");
        return new MessageDigestPasswordEncoder("MD5");
    }

}
