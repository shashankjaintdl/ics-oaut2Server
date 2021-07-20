package com.ics.oauth2server.security.twilio.service;


import com.ics.oauth2server.security.twilio.SMSSender;
import com.ics.oauth2server.security.twilio.request.SMSRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final SMSSender smsSender;

    @Autowired
    public TwilioService(@Qualifier("twilio") TwilioSMSSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSMS(SMSRequest smsRequest) {
        smsSender.smsSend(smsRequest);
    }

}
