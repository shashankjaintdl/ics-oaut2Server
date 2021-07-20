package com.ics.oauth2server.security.twilio;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializerConfiguration {

    public static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializerConfiguration.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioInitializerConfiguration(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(
                twilioConfiguration.getsId(),
                twilioConfiguration.getAuthToken()
        );
        LOGGER.info("Twilio initialized ... with account SID {}", twilioConfiguration.getsId());

    }
}
