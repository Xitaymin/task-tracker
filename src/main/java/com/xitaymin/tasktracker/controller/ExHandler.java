package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.model.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleException(IllegalArgumentException e) {
        Response response = new Response(e.getMessage().split("\n"));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> handleException(NoSuchElementException e) {
        Response response = new Response(e.getMessage().split("\n"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
