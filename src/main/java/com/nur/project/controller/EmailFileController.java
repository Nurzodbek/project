package com.nur.project.controller;

import com.nur.project.model.EmailFile;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class EmailFileController {
    private EmailFilePgClient emailFilePgClient;
    

    public EmailFileController(Vertx vertx){
        this.emailFilePgClient = new EmailFilePgClient(vertx);
    }

    public EmailFileController() {
    }

    public Future<Long> addEmailFile(Long loginId,EmailFile emailFile){
        return emailFilePgClient.addEmailFileCommand(1L, emailFile);
    }

    public Future<EmailFile> getEmailFile(Long loginId, Long emailFileId){
        return emailFilePgClient.getEmailFileCommand(loginId, emailFileId);

    }
    public Future<Long> updateEmailFile(Long loginId,EmailFile emailFile){
        return emailFilePgClient.updateEmailFileCommand(loginId,emailFile);
    }
    public Future<Long> deleteEmailFile(Long loginId ,Long emailFileId){
        return emailFilePgClient.deleteEmailFileCommand(loginId, emailFileId);
    }
}