package com.nur.project.verticle;
import java.util.HashSet;
import java.util.Set;
import com.nur.project.controller.FileController;
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

    @Override
    public void start(Future<Void> future) throws Exception {
        this.fileController = new FileController(vertx);

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
      //router.get("/api/file").handler(this::getFile);
        


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

    

}