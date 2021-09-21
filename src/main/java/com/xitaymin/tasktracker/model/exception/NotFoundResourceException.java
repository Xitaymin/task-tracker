package com.xitaymin.tasktracker.model.exception;

public class NotFoundResourceException extends BaseApplicationException {
    public NotFoundResourceException(String message) {
        super(message);
    }

    public NotFoundResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
