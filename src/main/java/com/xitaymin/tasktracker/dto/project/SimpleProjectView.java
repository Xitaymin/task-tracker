package com.xitaymin.tasktracker.dto.project;

public class SimpleProjectView {
    private final long id;
    private final String name;
    private final Long productOwner;

    public SimpleProjectView(long id, String name, Long productOwner) {
        this.id = id;
        this.name = name;
        this.productOwner = productOwner;
    }
}
