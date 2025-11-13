package com.emailclassifier.model;

public class EmailResponse {
    private String category;  // Spam, Work, Personal

    public EmailResponse() {}

    public EmailResponse(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}