package com.ics.oauth2server.security.twilio.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSRequest {
    private String phoneNo;
    private String message;

    public SMSRequest(@JsonProperty("phoneNo") String phoneNo,
                      @JsonProperty("message") String message) {
        this.phoneNo = phoneNo;
        this.message = message;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[SMS request phoneNo = " + phoneNo + " and Message = " + message + "]";
    }
}
