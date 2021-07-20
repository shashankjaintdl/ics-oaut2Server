package com.ics.oauth2server.exceptions;

import com.ics.oauth2server.helper.APIError;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.user.exception.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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


}
