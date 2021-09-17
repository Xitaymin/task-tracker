package com.xitaymin.tasktracker.model.dto;

public class Response {

    private String[] messages;

    public Response() {
    }

    public Response(String[] messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
