package com.ics.oauth2server.useraccount.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(){ super(); }
    public InvalidPasswordException(String message){super(message);}
    public InvalidPasswordException(String message, Throwable throwable){super(message,throwable);}
}
