package com.xitaymin.tasktracker.model.exception;

public class BaseApplicationException extends RuntimeException {
    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
