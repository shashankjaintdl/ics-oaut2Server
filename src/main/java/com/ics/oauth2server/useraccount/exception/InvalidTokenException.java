package com.ics.oauth2server.useraccount.exception;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(){ super(); }
    public InvalidTokenException(String message){ super(message); }
}
