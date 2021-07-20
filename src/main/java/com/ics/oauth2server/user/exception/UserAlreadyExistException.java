package com.ics.oauth2server.user.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(){super();}

    public UserAlreadyExistException(String message){super(message);}

    public UserAlreadyExistException(String message, Throwable throwable){super(message,throwable);}
}
