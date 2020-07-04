package com.nur.project;

import com.nur.project.verticle.ServerVerticle;
import io.vertx.core.Vertx;

public class MainVerticle{
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ServerVerticle());
  }
}