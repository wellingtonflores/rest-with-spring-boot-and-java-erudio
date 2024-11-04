package com.erudio.rest_with_spring_boot_and_java_erudio;

public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }
}
