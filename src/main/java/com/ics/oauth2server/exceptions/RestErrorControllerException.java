package com.ics.oauth2server.exceptions;

import com.ics.oauth2server.helper.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestController
public class RestErrorControllerException implements ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorControllerException.class);

    private Collection<StackTraceElement> stackTraceElements;
    private APIResponse<Object> apiResponse;
    private final ErrorAttributes errorAttributes;
    private static final String PATH = "/error";

    public RestErrorControllerException(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH)
    public ResponseEntity<Object> error(Exception ex,WebRequest webRequest, HttpServletRequest httpServletRequest) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        stackTraceElements =  Arrays
                .stream(ex.getStackTrace())
                .limit(10)
                .collect(Collectors.toList());
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        LOGGER
                .warn("Forwarded Error Request: {} ", attrs.get("path"), (Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception"));
        String statusCode =  attrs.get("status").toString();
        String status =  attrs.get("error").toString();
        String message = "Forwarded Error Request: " + attrs.get("path").toString();
        apiResponse = new APIResponse<>(
                Integer.parseInt(statusCode),
                status,
                message,
                stackTraceElements,
                httpServletRequest);
        LOGGER.warn("{}",apiResponse);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

}
