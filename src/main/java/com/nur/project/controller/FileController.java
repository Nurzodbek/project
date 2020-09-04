package com.nur.project.controller;

// import com.nur.project.controller.FilePgClient;
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

    public Future<File> getFile(Long loginId,Long fileId){
        System.out.println(filePgClient.getFileCommand(loginId, fileId).result());
        return  filePgClient.getFileCommand(loginId, fileId);
    }

    public Future<Long> updateFile(Long loginId,File file){
        return filePgClient.updateFileCommand(loginId, file);
    }

    public Future<Long> deleteFile(Long loginId,Long fileId){
        return filePgClient.deleteFileCommand(loginId, fileId);
    }
}