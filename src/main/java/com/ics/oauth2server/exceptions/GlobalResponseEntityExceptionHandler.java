package com.ics.oauth2server.exceptions;

import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalResponseEntityExceptionHandler.class);

    private Collection<StackTraceElement> stackTraceElements;
    HelperExtension helperExtension = new HelperExtension();

    private APIResponse apiResponse;

    public GlobalResponseEntityExceptionHandler(){
        this.stackTraceElements = new ArrayList<>();
    }
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        String message = "";
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        if(!helperExtension.isNullOrEmpty(ex.getSupportedMethods()) && !helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage() + " and supported method is "+ Arrays.stream(ex.getSupportedMethods())
                    .findFirst()
                    .orElse("Try with other supported methods");
        }
        apiResponse = new APIResponse<>(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.toString(),
                message,
                stackTraceElements,
                request);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        String message = "";
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        if(!helperExtension.isNullOrEmpty(ex.getMessage()) && !helperExtension.isNullOrEmpty(ex.getSupportedMediaTypes())){
            message = ex.getMessage() + " and supported media type is "+ ex.getSupportedMediaTypes();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(),
                message,
                stackTraceElements,
                request);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "";
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        if(!helperExtension.isNullOrEmpty(ex.getMessage()) && !helperExtension.isNullOrEmpty(ex.getSupportedMediaTypes())){
            message = ex.getMessage() + " and supported media type is "+ ex.getSupportedMediaTypes();
        }
        apiResponse = new APIResponse<>(
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.toString(),
                message,
                stackTraceElements,
                request);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleServletRequestBindingException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "";
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage() ;
        }
        apiResponse = new APIResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                message,
                stackTraceElements,
                request);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "";
        stackTraceElements =  Arrays.stream(ex.getStackTrace()).limit(10).collect(Collectors.toList());
        if(!helperExtension.isNullOrEmpty(ex.getMessage())){
            message = ex.getMessage() ;
        }
        apiResponse = new APIResponse<>(
                status.value(),
                status.name(),
                message,
                stackTraceElements,
                request);
        LOGGER.error("{}",ex);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }
}
