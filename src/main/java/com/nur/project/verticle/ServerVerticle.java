package com.nur.project.verticle;
import java.util.HashSet;
import java.util.Set;

import com.nur.project.controller.EmailController;
import com.nur.project.controller.EmailFileController;
import com.nur.project.controller.FileController;
import com.nur.project.model.Email;
import com.nur.project.model.EmailFile;
import com.nur.project.model.File;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.impl.BodyHandlerImpl;

public class ServerVerticle extends AbstractVerticle {
    private FileController fileController;
    private EmailController emailController;
    private EmailFileController emailFileController;

    @Override
    public void start(Future<Void> future) throws Exception {
        this.fileController = new FileController(vertx);
        this.emailController = new EmailController(vertx);
        this.emailFileController = new EmailFileController(vertx);
        Router router = Router.router(vertx);

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");


        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.PUT);
        allowedMethods.add(HttpMethod.DELETE);



        router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
        router.route().handler(new BodyHandlerImpl());
        router.route("/api/*").handler(BodyHandler.create());
        
        
        
        router.post("/api/file").handler(this::addFile);
        router.get("/api/file/:id").handler(this::getFile);
        router.put("/api/file/:id").handler(this::updateFile);
        router.delete("/api/file/:id").handler(this::deleteFile);


        router.post("/api/email").handler(this::addEmail);
        router.get("/api/email/:id").handler(this::getEmail);
        router.put("/api/email/:id").handler(this::updateEmail);
        router.delete("/api/email/:id").handler(this::deleteEmail);

        router.post("/api/emailFile").handler(this::addEmailFile);
        router.get("/api/emailFile/:id").handler(this::getEmailFile);
        router.put("/api/emailFile/:id").handler(this::updateEmailFile);
        router.delete("/api/emailFile/:id").handler(this::deleteEmailFile);
        
        vertx.createHttpServer().requestHandler(router).listen(8080,ar ->{
            if(ar.succeeded())
                System.out.println("Listen 8080 port");
            else
                System.out.println("Can not listen 8080 port");
        });
    }

    private void addFile(RoutingContext routingContext){
        File file = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(),File.class);
        this.fileController.addFile(1L, file).onComplete(ar -> {
            if(ar.succeeded()){
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            }
            if(ar.failed()){
                routingContext.response().end(Json.encodePrettily(ar.cause()));
            }
        });
    }

    private void getFile(RoutingContext routingContext){
        Long fileId = Long.parseLong(routingContext.request().getParam("id"));
        this.fileController.getFile(1L, fileId).onComplete(ar ->{
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                routingContext.response().end(Json.encodePrettily(ar.cause()));
        });
    }
    

    private void updateFile(RoutingContext routingContext){
        File file = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(),File.class);
        file.setFileId(Long.parseLong(routingContext.request().getParam("id")));
        this.fileController.updateFile(1L, file).onComplete(ar ->{
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else 
                routingContext.response().end(Json.encodePrettily(ar.cause()));
        });

    }

    private void deleteFile(RoutingContext routingContext){
        Long fileId = Long.parseLong(routingContext.request().getParam("id"));
        this.fileController.deleteFile(1L, fileId).onComplete(ar ->{
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else 
                routingContext.response().end(Json.encodePrettily(ar.cause()));
        });
    }


    private void addEmail(RoutingContext routingContext){
        Email email = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(), Email.class);
        
        if(email.getFiles().isEmpty()){
            
            
            this.emailController.addEmail(1L, email).onComplete(ar -> {
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                routingContext.response().end(Json.encodePrettily(ar.cause()));
            });


        }else{
            this.emailController.addEmail(1L, email).onComplete(ar ->{
                if(ar.succeeded()){
                    for (File file : email.getFiles()) {
                        this.fileController.addFile(1L, file).onComplete(ar1 -> {
                            if(ar1.succeeded()){
                                EmailFile emailFile = new EmailFile(ar.result(), ar1.result());
                                this.emailFileController.addEmailFile(1L, emailFile).onComplete(ar2 ->{
                                    if(ar2.succeeded()){
                                        routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar2.result()));
                                    }else{
                                        routingContext.response().end(Json.encodePrettily(ar2.cause()));
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }


    private void getEmail(RoutingContext routingContext) {
    Long emailId = Long.parseLong(routingContext.request().getParam("id"));
    this.emailController.getEmail(1l, emailId).onComplete( ar->{
        if (ar.succeeded()) {
            Long emailFileId = Long.parseLong(routingContext.request().getParam("id"));
                this.emailFileController.getEmailFileList(1L, emailFileId).onComplete(ar1 -> {
                    
                        for(Long fileId: ar1.result()){
                            
                            this.fileController.getFile(1l, fileId).onComplete(ar2->{
                                if(ar2.succeeded()){
                                    routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar2.result()));
                                }else{
                                    routingContext.response().end(Json.encodePrettily(ar2.cause()));
                                }
                            });     
                        
                    }    
                });
                    routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
        } else {
                 routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.cause()));
                }
    });


    // this.emailController.getEmail(1L, emailId).onComplete(ar -> {

    //   if (ar.succeeded()) {
    //     // Long emailFileId = Long.parseLong(routingContext.request().getParam("id"));
    //         this.emailFileController.getEmailFileList(1L, ar.result()).onComplete(ar1 ->{
    //           if(ar1.succeeded()){
    //               System.out.println(ar1.result());
    //                 if(!ar1.result().isEmpty())
    //                     for(Long fileId: ar1.result()){
    //                         this.fileController.getFile(1l, fileId).onComplete(ar2->{
    //                             if(ar2.succeeded())
    //                                 routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar2.result()));
    //                         });
    //                     }
    //           }                                    routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar1.result()));

    //         });
    //         routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
    //     }
    // });
  }
    private void updateEmail(RoutingContext routingContext){
        Email email = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(),Email.class);
        email.setEmailId(Long.parseLong(routingContext.request().getParam("id")));
        this.emailController.updateEmail(1L, email).onComplete(ar ->{
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                routingContext.response().end(Json.encodePrettily(ar.cause()));    
        });    
    }

    private void deleteEmail(RoutingContext routingContext){
        Long emailId = Long.parseLong(routingContext.request().getParam("id"));
        this.emailController.deleteEmail(1L, emailId).onComplete(ar -> {
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                routingContext.response().end(Json.encodePrettily(ar.cause()));
        });
    }

    private void addEmailFile(RoutingContext routingContext){
        EmailFile emailFile = Json.decodeValue(routingContext.getBodyAsJson().toBuffer(),EmailFile.class);
        this.emailFileController.addEmailFile(1L, emailFile).onComplete(ar ->{
            if(ar.succeeded()){
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            }    
            if(ar.failed()){
                routingContext.response().end(Json.encodePrettily(ar.cause()));    
            }
        });
    }

    private void getEmailFile(RoutingContext routingContext){
        Long emailFileId = Long.parseLong(routingContext.request().getParam("id"));
        this.emailFileController.getEmailFile(1L, emailFileId).onComplete(ar ->{
            if(ar.succeeded())
                routingContext.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                routingContext.response().end(Json.encodePrettily(ar.cause()));    
            });
    }
    
    private void updateEmailFile(RoutingContext rc){
       EmailFile emailFile = Json.decodeValue(rc.getBodyAsJson().toBuffer(),EmailFile.class);
       emailFile.setEmailFileId(Long.parseLong(rc.request().getParam("id")));
       this.emailFileController.updateEmailFile(1L, emailFile).onComplete(ar ->{
            if(ar.succeeded())
                rc.response().setStatusCode(201).end(Json.encodePrettily(ar.result()));
            else
                rc.response().end(Json.encodePrettily(ar.cause()));
       });
    }

    private void deleteEmailFile(RoutingContext rc){
        Long emailFileId = Long.parseLong(rc.request().getParam("id"));
        this.emailFileController.deleteEmailFile(1L, emailFileId).onComplete(ar ->{
            if(ar.succeeded())
                rc.response().setStatusCode(201).end(Json.encodePrettily(ar.result())); 
            else
                rc.response().end(Json.encodePrettily(ar.cause())); 
        });
    }

}