package com.ics.oauth2server.security.twilio.service;

import com.ics.oauth2server.security.twilio.SMSSender;
import com.ics.oauth2server.security.twilio.TwilioConfiguration;
import com.ics.oauth2server.security.twilio.request.SMSRequest;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSMSSender implements SMSSender {

    public final Logger LOGGER = LoggerFactory.getLogger(TwilioSMSSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    TwilioSMSSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void smsSend(SMSRequest smsRequest) {
        try {
            if (isPhoneNoValid(smsRequest.getPhoneNo())) {
                PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNo());
                PhoneNumber from = new PhoneNumber(twilioConfiguration.getPhoneNo());
                String message = smsRequest.getMessage();
                MessageCreator creator = Message.creator(to, from, message);
                creator.create();
                LOGGER.info("sms sent... {}", smsRequest);
            } else {
                throw new IllegalArgumentException("Phone number [ " + smsRequest.getPhoneNo() + " ] is not valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Boolean isPhoneNoValid(String phoneNo) {
        phoneNo = phoneNo.replaceAll("[\\s()-]", "");
        if ("".equals(phoneNo)) {
            return false;
        }
        try {
            com.twilio.rest.lookups.v1.PhoneNumber.fetcher(new PhoneNumber(phoneNo)).fetch();
            return true;
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            LOGGER.info("Phone number is not valid {}", phoneNo);
            throw new IllegalArgumentException("Phone number is not valid!");
        }
    }


}
