package com.xitaymin.tasktracker.model.service.exceptions;

public class TeamMembersNumberExceedException extends BaseApplicationException {
    public TeamMembersNumberExceedException(String message) {
        super(message);
    }

    public TeamMembersNumberExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
