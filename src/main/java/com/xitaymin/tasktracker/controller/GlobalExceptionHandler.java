package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.Response;
import com.xitaymin.tasktracker.model.exception.BaseApplicationException;
import com.xitaymin.tasktracker.model.exception.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.exception.NotFoundResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<Response> handleException(NotFoundResourceException e) {
        log.info(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestParameterException.class)
    public ResponseEntity<Response> handleException(InvalidRequestParameterException e) {
        log.info(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<Response> handleException(BaseApplicationException e) {
        log.info(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        log.warn(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
