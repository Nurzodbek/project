package com.nur.project.controller;

import com.nur.project.model.Email;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class EmailPgClient {
    private PgPool pgPool;

    public EmailPgClient(Vertx vertx){
        PgConnectOptions pgConnectOptions = new PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("File")
        .setUser("File")
        .setPassword("123");
        
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

        this.pgPool = PgPool.pool(vertx,pgConnectOptions ,poolOptions);

    }

    public EmailPgClient() {
    }

    
    public Future<Long> addEmailCommand(Long loginId,Email email){
        Promise<Long> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_add AS email_id FROM registration.email_add($1,$2);")
        .execute(Tuple.of(loginId, email.getEmailAddress()), ar ->{
            if(ar.succeeded()){
                System.out.println("Got " + ar.result().size() + " rows");
                for (Row row : ar.result()) {
                    promise.complete(row.getLong("email_id"));
                }
            }else
                    promise.fail(ar.cause());
        });
        return promise.future();
    }

    

    public Future<Email> getEmailCommand(Long loginId, Long emailId){
        Promise<Email> promise = Promise.promise();
        pgPool.preparedQuery("SELECT * FROM registration.email_get($1,$2);")
        .execute(Tuple.of(loginId, emailId),
            ar ->{
                    if (ar.succeeded()){
                        System.out.println("Get "+ar.result().size() + " rows");
                        for (Row row : ar.result()) {
                            Email email = createEmailRows(row);
                            promise.complete(email);
                        }    
                        if(promise.tryComplete()){

                        }
                    }else
                        promise.fail(ar.cause());
        
                
            });
        return promise.future();
    }


    private Email createEmailRows(Row row){
        Email email = new Email();
        email.setEmailId(row.getLong("email_id"));
        email.setEmailAddress(row.getString("email_address"));
        return email;   
    }

    public Future<Long> updateEmailCommand(Long loginId,Email email){
        Promise<Long> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_update AS email_id FROM registration.email_update($1,$2,$3);")
        .execute(Tuple.of(loginId, email.getEmailId(),email.getEmailAddress()), ar ->{
                if(ar.succeeded()){
                    System.out.println("Got " + ar.result().size() + " rows");
                    for (Row row : ar.result()) {
                        promise.complete(row.getLong("email_id"));
                    }
                }else
                    promise.fail(ar.cause());
            });
        
        
        return promise.future();
    }

    public Future<Long> deleteEmailCommand(Long loginId,Long emailId){
        Promise<Long> promise = Promise.promise();
        pgPool.preparedQuery("SELECT email_delete AS email_id FROM registration.email_delete($1, $2);")
        .execute(Tuple.of(loginId, emailId), ar ->{
            if(ar.succeeded()){
                System.out.println("Got "+ar.result().size() + " rows");
                for(Row row : ar.result()){
                    promise.complete(row.getLong("email_id"));
                }
            }else
                    promise.fail(ar.cause());
        });
        return promise.future();
    }

}