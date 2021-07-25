package com.ics.oauth2server.useraccount.exception;

public class AccountNotExistException extends RuntimeException{
    public AccountNotExistException(){super();}
    public AccountNotExistException(String message){super(message);}
}
