package com.nur.project.model;

import java.util.List;

public class Email {
  private Long emailId;
  private String emailAddress;
  private List<File> files;


  public Email() {
  }

  public Email(Long emailId, String emailAddress) {
    this.emailId = emailId;
    this.emailAddress = emailAddress;
  }

  public Email(String emailAddress, List<File> files) {
    this.emailAddress = emailAddress;
    this.files = files;
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

  public List<File> getFiles() {
    return files;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

}
