package com.ics.oauth2server.helper;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@ResponseBody
@NoArgsConstructor
public @Data class APIResponse<T> {

    private Date timestamp;
    private Integer statusCode;
    private String status;
    private String path;
    private String requestMethod;
    @Nullable
    private String message;
    @Nullable
    private List<T> object;
    private Integer[] noOfPage = {};
    private Integer totalItems = 0;
    @Nullable
    private Collection<StackTraceElement> stackTraceElement = new ArrayList<>();


    public APIResponse(Integer statusCode, String status, String message, @Nullable List<T> object) {
        super();
        this.timestamp = new Date();
        this.status = status.substring(3).trim();
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
        HelperExtension.Print(toString());
    }

    public APIResponse(Integer statusCode, String status, String message, @Nullable List<T> object, Integer[] noOfPage) {
        this(statusCode, status, message, object);
        this.noOfPage = noOfPage;
    }


    public APIResponse(Integer statusCode, String status, String message, @Nullable List<T> object, Integer[] noOfPage, Integer totalItems) {
        this(statusCode, status, message, object, noOfPage);
        this.totalItems = totalItems;
    }


    public APIResponse(Integer statusCode, String status, String message, @Nullable List<T> object, Integer totalItems) {
        this(statusCode, status, message, object);
        this.totalItems = totalItems;
    }


    /***
     *
     *
     * HttpServletRequest Type starts
     *
     * **/

    public APIResponse(@Nullable HttpServletRequest httpServletRequest) {
        super();
        this.path = httpServletRequest.getRequestURI();
        this.requestMethod = httpServletRequest.getMethod();
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable List<T> object,  @Nullable HttpServletRequest httpServletRequest) {
        this(httpServletRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();;
        this.message = message;
        this.object = object;
        HelperExtension.Print(toString());
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable List<T> object, Integer totalItems,@Nullable HttpServletRequest httpServletRequest) {
        this(statusCode,status,message,object,httpServletRequest);
        this.totalItems = totalItems;
    }


    // User below for any exception or error occurs area of Http Request Type

    public APIResponse(Integer statusCode, String status ,String message, @Nullable List<T> object, Integer totalItems, @Nullable Collection<StackTraceElement> stackTraceElement,@Nullable HttpServletRequest httpServletRequest) {
        this(statusCode,status,message,object,totalItems,httpServletRequest);
        this.stackTraceElement = stackTraceElement;
    }

    public APIResponse(Integer statusCode, String status ,String message, @Nullable List<T> object,Integer[] noOfPage ,Integer totalItems, @Nullable Collection<StackTraceElement> stackTraceElement,@Nullable HttpServletRequest httpServletRequest) {
        this(statusCode,status,message,object,totalItems,stackTraceElement,httpServletRequest);
        this.noOfPage = noOfPage;
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable Collection<StackTraceElement> stackTraceElement, @Nullable HttpServletRequest httpServletRequest) {
        this(httpServletRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.stackTraceElement = stackTraceElement;
        this.object = new ArrayList<>();
        this.totalItems = 0;
        HelperExtension.Print(toString());
    }

    /***
     *
     *
     *  Web Request Type starts
     *
     * **/

    public APIResponse(@Nullable WebRequest request) {
        super();
        this.path = request.getDescription(true).substring(4).split(";")[0];
        this.requestMethod = ((ServletWebRequest) request).getHttpMethod().toString();
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable List<T> object,  @Nullable WebRequest webRequest) {
        this(webRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();;
        this.message = message;
        this.object = object;
        HelperExtension.Print(toString());
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable List<T> object, Integer totalItems,@Nullable WebRequest webRequest) {
        this(statusCode,status,message,object,webRequest);
        this.totalItems = totalItems;
    }
    // User below for any exception or error occurs area of Web Request Type
    public APIResponse(Integer statusCode, String status , String message, @Nullable List<T> object, Integer totalItems, @Nullable Collection<StackTraceElement> stackTraceElement,@Nullable WebRequest webRequest) {
        this(statusCode,status,message,object,totalItems,webRequest);
        this.stackTraceElement = stackTraceElement;
    }

    public APIResponse(Integer statusCode, String status ,String message, @Nullable List<T> object,Integer[] noOfPage ,Integer totalItems, @Nullable Collection<StackTraceElement> stackTraceElement,@Nullable WebRequest webRequest) {
        this(statusCode,status,message,object,totalItems,stackTraceElement,webRequest);
        this.noOfPage = noOfPage;
    }

    public APIResponse(Integer statusCode, String status , String message, @Nullable Collection<StackTraceElement> stackTraceElement, @Nullable WebRequest webRequest) {
        this(webRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.stackTraceElement = stackTraceElement;
        this.object = new ArrayList<>();
        this.totalItems = 0;
        HelperExtension.Print(toString());
    }


    @Override
    public String toString() {
        return "API Response [ status Code = '" + statusCode + "' Status = '" + status + "' " +
                "Message = '" + message + "' Object = '" + object + "' " +
                "noOfPages = '" + noOfPage + "' Total Items = '" + totalItems + "' ]";
    }

}
