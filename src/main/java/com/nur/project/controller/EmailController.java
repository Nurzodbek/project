package com.nur.project.controller;

import com.nur.project.model.Email;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class EmailController {
    private EmailPgClient emailPgClient;

    public EmailController(Vertx vertx){
        this.emailPgClient = new EmailPgClient(vertx);
    }

    public EmailController() {
    }

     public Future<Long> addEmail(Long loginId , Email email){
         return emailPgClient.addEmailCommand(1L, email);
     }
     public Future<Email> getEmail(Long loginId,Long emailId){
         return emailPgClient.getEmailCommand(loginId, emailId);
     }
     public Future<Long> updateEmail(Long loginId,Email email){
         return emailPgClient.updateEmailCommand(loginId, email);
     }

     public Future<Long> deleteEmail(Long loginId,Long emailId){
         return emailPgClient.deleteEmailCommand(loginId, emailId);
     }

}