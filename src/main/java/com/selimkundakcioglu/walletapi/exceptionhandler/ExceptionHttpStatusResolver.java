package com.selimkundakcioglu.walletapi.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Component
public class ExceptionHttpStatusResolver {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_CLASS_HTTP_STATUS_MAP = new HashMap<>();

    static {
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(NoHandlerFoundException.class, NOT_FOUND);
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(HttpRequestMethodNotSupportedException.class, METHOD_NOT_ALLOWED);
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(HttpMessageNotReadableException.class, BAD_REQUEST);
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(HttpMediaTypeNotSupportedException.class, UNSUPPORTED_MEDIA_TYPE);
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(MethodArgumentTypeMismatchException.class, BAD_REQUEST);
        EXCEPTION_CLASS_HTTP_STATUS_MAP.put(MissingServletRequestParameterException.class, BAD_REQUEST);
    }

    public HttpStatus resolve(Exception e) {
        return EXCEPTION_CLASS_HTTP_STATUS_MAP.getOrDefault(e.getClass(), INTERNAL_SERVER_ERROR);
    }
}
