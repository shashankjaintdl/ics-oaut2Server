package com.ics.oauth2server.useraccount.exception.handler;

import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.useraccount.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class UserAccountExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountExceptionHandler.class);
    private APIResponse apiResponse;
    private Collection<StackTraceElement> stackTraceElements;
    HelperExtension helperExtension = new HelperExtension();

    @ExceptionHandler(value = {InvalidTokenException.class})
    ResponseEntity<Object> handleInvalidSecureTokenException(RuntimeException ex, HttpServletRequest httpServletRequest){
        String message = "";
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage();
        }
        apiResponse = new APIResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                message,
                Collections.emptyList().stream().collect(Collectors.toList()),
                httpServletRequest);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
}

