package com.nur.project.controller;

import com.nur.project.controller.FilePgClient;
import com.nur.project.model.File;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
public class FileController {

    private FilePgClient filePgClient;

    public FileController(Vertx vertx){
        this.filePgClient = new FilePgClient(vertx);
    }

    public FileController() {
    }

    public Future<Long> addFile(Long loginId,File file){
        return filePgClient.addFileCommand(loginId, file);
    }
}