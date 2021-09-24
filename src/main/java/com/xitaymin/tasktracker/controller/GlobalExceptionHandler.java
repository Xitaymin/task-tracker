package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.ResponseError;
import com.xitaymin.tasktracker.model.service.exceptions.BaseApplicationException;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseError handleException(NotFoundResourceException e, WebRequest request) {
        log.info("Not found requested entity.  {} {}", e.getMessage(), request, e);
        return new ResponseError(e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidRequestParameterException.class)
    public ResponseError handleException(InvalidRequestParameterException e, WebRequest request) {
        log.info("Validation failed. {} {}", e.getMessage(), request, e);
        return new ResponseError(e.getMessage());
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(BaseApplicationException.class)
    public ResponseError handleException(BaseApplicationException e, WebRequest request) {
        log.info("Business error caught. {} {}", e.getMessage(), request, e);
        return new ResponseError(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseError handleException(Exception e, WebRequest request) {
        log.error("Not business exception caught. {} {}", e.getMessage(), request, e);
        return new ResponseError(e.getMessage());
    }
}
