package com.ics.oauth2server.useraccount.exception;
public class InvalidOTPException extends RuntimeException{
    public InvalidOTPException(){super();}
    public InvalidOTPException(String message){super(message);}
}
