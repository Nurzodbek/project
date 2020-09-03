package com.nur.project.model;

public class EmailFile {

  private Long emailFileId;
  private Long emailId;
  private Long fileId;

  public EmailFile() {
  }

  public EmailFile(Long emailFileId, Long emailId, Long fileId) {
    this.emailFileId = emailFileId;
    this.emailId = emailId;
    this.fileId = fileId;
  }

  public Long getEmailFileId() {
    return emailFileId;
  }

  public void setEmailFileId(Long emailFileId) {
    this.emailFileId = emailFileId;
  }

  public Long getEmailId() {
    return emailId;
  }

  public void setEmailId(Long emailId) {
    this.emailId = emailId;
  }

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public EmailFile(Long emailId, Long fileId) {
    this.emailId = emailId;
    this.fileId = fileId;
  }


}
