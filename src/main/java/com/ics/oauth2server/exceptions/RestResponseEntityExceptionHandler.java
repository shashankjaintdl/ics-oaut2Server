package com.ics.oauth2server.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.user.exception.UserAlreadyExistException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {


    public static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    private Collection<StackTraceElement> stackTraceElements;
    HelperExtension helperExtension = new HelperExtension();

    private APIResponse apiResponse;

    public RestResponseEntityExceptionHandler(){
        this.stackTraceElements = new ArrayList<>();
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    protected ResponseEntity<Object> handleResourceAlreadyExistException(RuntimeException ex, HttpServletRequest httpServletRequest){
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.toString(),
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintException(Exception ex, HttpServletRequest httpServletRequest){
        LOGGER.info("Handle Constraint Violation Exception");
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest){
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getFieldError().getDefaultMessage())){
            message = ex.getFieldError().getDefaultMessage();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(value = {JsonParseException.class})
    protected ResponseEntity<Object> handleJSONParseException(JsonParseException ex, HttpServletRequest httpServletRequest){
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(value = {HibernateException.class})
    protected ResponseEntity<Object> handleInternalServerExceptions(Exception ex,HttpStatus httpStatus, HttpServletRequest httpServletRequest){
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }





}
