package com.ics.oauth2server.security.twilio;


import com.ics.oauth2server.security.twilio.request.SMSRequest;

public interface SMSSender {
    void smsSend(SMSRequest smsRequest);
}
