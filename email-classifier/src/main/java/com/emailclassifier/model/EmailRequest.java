package com.emailclassifier.model;

public class EmailRequest {
    private String content; // the email text to classify

    public EmailRequest() {}

    public EmailRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}