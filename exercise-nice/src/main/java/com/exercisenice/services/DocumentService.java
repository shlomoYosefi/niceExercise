package com.exercisenice.services;

import com.exercisenice.loggers.DocumentLogger;
import com.exercisenice.messageBuilder.DocumentMessageBuilder;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final int maxAttempts=5;
    private final int delay = 2000;
    private static Logger logger =DocumentLogger.logger;



    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    //add document to DB and try again 5 times in case of exception internalServerError
    @Retryable(value = PersistenceException.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String addDocument(Document document){
        logger.info(DocumentMessageBuilder.tryAddDocument +document.toString());
        try {
            document.setCreationDate(LocalDateTime.now());
            documentRepository.save(document);
            String message = DocumentMessageBuilder.addSuccess;
            logger.info(message);
            return message;
        }
        catch (Exception e) {
            throw e;
        }
    }



    //get document from DB by id and try again 5 times in case of exception internalServerError
    @Retryable(value = PersistenceException.class, maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public Document getDocumentById(Long id){
        logger.info(DocumentMessageBuilder.tryGetDocument+id);
        return documentRepository.findById(id).get();

    }



    //get all documents from DB and try again 5 times in case of exception internalServerError
    @Retryable(value = PersistenceException.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public List<Document> getDocuments(){
        logger.info(DocumentMessageBuilder.tryGetAllDocument);
        return documentRepository.findAll();
    }


    //update document in DB by id and try again 5 times in case of exception internalServerError
    @Retryable(value = PersistenceException.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String updateDocument(Document document,Long id){
        logger.info(DocumentMessageBuilder.tryUpdateDocument +document.toString() );
             documentRepository.findById(id).get();
             documentRepository.save(document);
             String message = DocumentMessageBuilder.updateSuccess;
             logger.info(message);
             return message;
    }


    //delete document from DB by id and try again 5 times in case of exception internalServerError
    @Retryable(value = HttpServerErrorException.InternalServerError.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String deleteDocument(Long id){
        logger.info(DocumentMessageBuilder.tryDeleteDocument+ id);
                documentRepository.findById(id).get();
                documentRepository.deleteById(id);
                String message =DocumentMessageBuilder.deleteSuccess;
                logger.info(message);
                return message;
       }
    }






























