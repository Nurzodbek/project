package com.nur.project.controller;

import com.nur.project.model.File;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class FilePgClient {
    private PgPool pgPool;

    public FilePgClient(Vertx vertx){
        PgConnectOptions connectOptions = new PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("File")
        .setUser("File")
        .setPassword("123");

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

        this.pgPool = PgPool.pool(vertx,connectOptions,poolOptions);
    
    }

    public FilePgClient() {
    }

    
    public Future<Long> addFileCommand(Long loginId , File file){
        Promise<Long> promise = Promise.promise();

        pgPool.preparedQuery("SELECT file_add AS file_id FROM registration.file_add($1, $2, $3, $4, $5, $6, $7);")
        .execute(Tuple.of(loginId, file.getName(),file.getDisplayName(),file.getUniqueName(),file.getDescription(),file.getSize(),file.getMimeType()), ar-> {
            if(ar.succeeded()){
                System.out.println("Got "+ar.result().size() + " rows ");
                for (Row row : ar.result()) {
                    promise.complete(row.getLong("file_id"));
                }
                if(promise.tryComplete()){

                }
            }else{
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }
}