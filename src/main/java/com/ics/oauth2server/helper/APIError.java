package com.ics.oauth2server.helper;


import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public @Data
class APIError {
    private Date timestamp;
    private Integer statusCode;
    private String status;
    private String requestMethod;
    @Nullable
    private List<String> errorMessage;
    private String path;
    @Nullable
    private List<StackTraceElement> stackTraceElement;

    public APIError() {

    }

    public APIError(Integer statusCode, String status, @Nullable List<String> errorMessage, @Nullable List<StackTraceElement> stackTraceElement, @Nullable HttpServletRequest httpServletRequest) {
        this(httpServletRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();
        this.errorMessage = errorMessage;
        this.stackTraceElement = stackTraceElement;
    }

    public APIError(@Nullable HttpServletRequest httpServletRequest) {
        super();
        this.path = httpServletRequest.getRequestURI();
        this.requestMethod = httpServletRequest.getMethod();
    }

    public APIError(Integer statusCode, String status, @Nullable List<String> errorMessage, @Nullable List<StackTraceElement> stackTraceElement, @Nullable WebRequest request) {
        this(request);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();
        this.errorMessage = errorMessage;
        this.stackTraceElement = stackTraceElement;
    }

    public APIError(@Nullable WebRequest request) {
        super();
        this.path = request.getDescription(true).substring(4).split(";")[0];
        this.requestMethod = ((ServletWebRequest) request).getHttpMethod().toString();
    }


}
