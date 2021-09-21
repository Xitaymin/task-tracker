package com.xitaymin.tasktracker.model.exception;

public class InvalidRequestParameterException extends BaseApplicationException {
    public InvalidRequestParameterException(String message) {
        super(message);
    }

    public InvalidRequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
