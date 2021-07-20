package com.ics.oauth2server.security.email;

import com.ics.oauth2server.security.email.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailServices {
    public void sendEmail(AbstractEmailContext context) throws MessagingException;
}
