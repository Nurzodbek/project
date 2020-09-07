package com.nur.project.controller;

import java.util.ArrayList;
import java.util.List;

import com.nur.project.model.EmailFile;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class EmailFilePgClient {
    private PgPool pgPool;

    public EmailFilePgClient(Vertx vertx){
        PgConnectOptions pgConnectOptions = new PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("File")
        .setUser("File")
        .setPassword("123");

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        
        this.pgPool = PgPool.pool(vertx,pgConnectOptions,poolOptions);
    }

    public EmailFilePgClient() {
    }

    public Future<Long> addEmailFileCommand(Long loginId,EmailFile emailFile){
        Promise<Long> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_file_add AS email_file_id FROM registration.email_file_add($1,$2,$3);")
        .execute(Tuple.of(loginId, emailFile.getEmailId(),emailFile.getFileId()), ar ->{
            if(ar.succeeded()){
                System.out.println("Got " + ar.result().size() + " rows");
                for (Row row : ar.result()) {
                    promise.complete(row.getLong("email_file_id"));
                }
            }else
                    promise.fail(ar.cause());
        });
        return promise.future();
    }


    public Future<EmailFile> getEmailFileCommand(Long loginId,Long emailFileId){
        Promise<EmailFile> promise = Promise.promise();
        pgPool.preparedQuery("SELECT * FROM registration.email_file_get($1,$2);")
        .execute(Tuple.of(loginId, emailFileId),
            ar -> {
                if(ar.succeeded()){
                    System.out.println("Get " +ar.result().size() + " rows");
                    for (Row row : ar.result()){
                        EmailFile emailFile = createEmailFileRows(row);
                        promise.complete(emailFile);
                    }
                    if(promise.tryComplete()){

                    }
                }else
                    promise.fail(ar.cause());
        });
        
        return promise.future();
    }

    private EmailFile createEmailFileRows(Row row){
        EmailFile emailFile = new EmailFile();
        emailFile.setEmailFileId(row.getLong("email_file_id"));
        emailFile.setEmailId(row.getLong("email_id"));
        emailFile.setFileId(row.getLong("file_id"));
        return emailFile;
    }

    public Future<List<Long>> getEmailFileIdCommand(Long loginId,Long emailId){
        Promise<List<Long>> promise = Promise.promise();
        pgPool.preparedQuery("SELECT * FROM registration.email_file_getid($1,$2);")
        .execute(Tuple.of(loginId, emailId),
        ar -> {
            if(ar.succeeded()){
                System.out.println("Get " + ar.result().size() + " rows");
                List<Long> ids = new ArrayList<Long>();
                for (Row row : ar.result()) {
                    EmailFile emailFile = createEmailFileRows(row);
                    ids.add(emailFile.getFileId());
                }
                promise.complete(ids);
            }else{
                promise.fail(ar.cause());
            }   
            });
        return promise.future();
    }

    

	public Future<Long> updateEmailFileCommand(Long loginId, EmailFile emailFile) {
        Promise<Long> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_file_update AS email_file_id FROM registration.email_file_update($1,$2,$3,$4);")
        .execute(Tuple.of(loginId,emailFile.getEmailFileId(),emailFile.getEmailId(),emailFile.getFileId()),ar ->{
            if(ar.succeeded()){
                System.out.println("Got " + ar.result().size() + " rows");
                for (Row row : ar.result()) {
                    promise.complete(row.getLong("email_file_id"));
                }
            }else
                promise.fail(ar.cause());
        });
        return promise.future();
    }
    

    public Future<List<Long>> deleteEmailFileCommand(Long loginId,Long emailFileId){
        Promise<List<Long>> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_file_delete AS email_file_id FROM registration.email_file_delete($1,$2);")
        .execute(Tuple.of(loginId, emailFileId), ar ->{
            if(ar.succeeded()){
                System.out.println("Get " +ar.result().size() + " rows");
                List<Long> idsList = new ArrayList<Long>();
                for (Row row : ar.result()){
                    idsList.remove(row.getLong("file_id")); 
                    promise.complete(idsList);
                }
            }else
                promise.fail(ar.cause());
        });

        return promise.future();
    }


}    




//private void addEmail(RoutingContext routingContext){
//     Email email = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(),Email.class);
//     if(files.isEmpty()){ // yoki email.getFiles.isEmpty() emailni ichida filelarni listi bo'ladi jsonga o'girganingda agar emailni ichida filelar bo'lsa ularni ham o'girib beradi.
//         this.emailController.addEmail(1L, email).onComplete(ar -> {
//             if(ar.succeeded())
//                 routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
//             else
//                 routingContext.response().end(Json.encodePrettily(ar.cause()));
//         });
//         } else
//     {
//         this.emailController.addEmail(1L, email).onComplete(ar -> {
//             if(ar.succeeded()) {
//                 for (File file : files
//                 ) {
//                     this.fileController.addFile(1l, file).onComplete(ar2 -> {
//                         if (ar2.succeeded()){
//                             this.emailFileController.addEmailFile(ar.result(), ar2.result()).onComplete(ar3->{
//                                if (ar3.succeeded()){
//                                    routingContext.response().end(Json.encodePrettily(ar3.result()));

//                                }
//                                else   routingContext.response().end(Json.encodePrettily(ar3.cause()));

//                             });
//                             routingContext.response().end(Json.encodePrettily(ar2.result()));

//                         }
//                         else
//                             routingContext.response().end(Json.encodePrettily(ar2.cause()));

//                     });
//                 }
//                 routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
//             }
//             else
//                 routingContext.response().end(Json.encodePrettily(ar.cause()));
//         });}

// }
// }