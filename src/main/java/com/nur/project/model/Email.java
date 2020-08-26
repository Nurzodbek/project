package com.nur.project.model;

public class Email {
    private Long emailId;
    private String emailAddress;
    

    public Email() {
    }

    public Email(Long emailId, String emailAddress) {
        this.emailId = emailId;
        this.emailAddress = emailAddress;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
}