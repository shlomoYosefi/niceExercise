package com.exercisenice.controllers;


import com.exercisenice.loggers.DocumentLogger;
import com.exercisenice.messageBuilder.DocumentMessageBuilder;
import com.exercisenice.models.Document;
import com.exercisenice.services.DocumentService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/documents")
@Validated
public class DocumentController {

    private final DocumentService documentService;
    private final int maxAttempts=5;
    private static Logger logger = DocumentLogger.logger;
    private String messageNotExists = DocumentMessageBuilder.notExists;


    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    //add document to DB
    @PostMapping
    public ResponseEntity<String> addDocument(@Valid @RequestBody Document document){
        try{
            return ResponseEntity.ok().body(documentService.addDocument(document));
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DocumentMessageBuilder.internalServerError);
        }
    }



    //get document by id from DB
    @GetMapping(path = "{id}")
    public ResponseEntity getDocumentById(@PathVariable("id")  Long id) {
        try{
            return ResponseEntity.ok().body(documentService.getDocumentById(id));
        }
        catch (NoSuchElementException e) {
            logger.warning(messageNotExists);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageNotExists);
        }
        catch (Exception e){
            logger.severe(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DocumentMessageBuilder.internalServerError);
        }
    }



    //get all document from DB
    @GetMapping
    public ResponseEntity getAllDocument(){
        try{
            return ResponseEntity.ok().body(documentService.getDocuments());
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DocumentMessageBuilder.internalServerError);
        }
    }



    //update document by id
    @PutMapping(path = "{id}")
    public ResponseEntity updateDocument(@PathVariable("id") Long id ,@Valid @RequestBody Document document) {
        try {
           return ResponseEntity.ok().body(documentService.updateDocument(document, id));
        }
        catch (NoSuchElementException e) {
            logger.warning(messageNotExists);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageNotExists);
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DocumentMessageBuilder.internalServerError);
        }
    }



    //delete document from DB by id
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteDocument(@PathVariable("id") Long id ){
        try {
            return ResponseEntity.ok(documentService.deleteDocument(id));
        }
        catch (NoSuchElementException e) {
            logger.warning(messageNotExists);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageNotExists);
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DocumentMessageBuilder.internalServerError);
        }
    }



}
