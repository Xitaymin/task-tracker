package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.Response;
import com.xitaymin.tasktracker.model.exception.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.exception.NotFoundResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<Response> handleException(NotFoundResourceException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestParameterException.class)
    public ResponseEntity<Response> handleException(InvalidRequestParameterException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
