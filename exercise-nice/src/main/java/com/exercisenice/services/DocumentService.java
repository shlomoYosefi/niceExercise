package com.exercisenice.services;



import com.exercisenice.loggers.DocumentLogger;
import com.exercisenice.messageBuilder.DocumentMessageBuilder;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final int maxAttempts=5;
    private final int delay = 2000;

    private static Logger logger = DocumentLogger.logger;


    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;

    }

    @Retryable(value = {SQLException.class},maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String addDocument(Document document) throws SQLException{
        logger.info(DocumentMessageBuilder.tryAddDocument +document.toString());
        try {
            document.setCreationDate(LocalDateTime.now());
            documentRepository.save(document);
            String message = DocumentMessageBuilder.addSuccess;
            logger.info(message);
            return message;
        }
//        catch (SQLException e) {
//            throw e;
//        }
        catch (Exception e) {
            throw e;
        }
    }


    @Retryable(value = SQLException.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public Document getDocumentById(Long id) throws SQLException {
        logger.info(DocumentMessageBuilder.tryGetDocument+id);
        try{
            return documentRepository.findById(id).get();
        }
        catch (Exception e) {
            throw e;
        }
    }




    @Retryable(value = SQLException.class,maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public List<Document> getDocuments() throws SQLException {
        logger.info(DocumentMessageBuilder.tryGetAllDocument);
        try {
            return documentRepository.findAll();
        }
        catch (Exception e) {
            throw e;
        }
    }



    @Retryable(value = {SQLException.class},maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String updateDocument(Document document,Long id) throws SQLException {
        logger.info(DocumentMessageBuilder.tryUpdateDocument +document.toString() );
        try{
             documentRepository.findById(id).get();
             documentRepository.save(document);
             String message = DocumentMessageBuilder.updateSuccess;
             logger.info(message);
             return message;
        }
        catch (Exception e) {
            throw e;
        }

    }



    @Retryable(value = {SQLException.class},maxAttempts = maxAttempts ,backoff = @Backoff(delay))
    public String deleteDocument(Long id) throws SQLException{
        logger.info(DocumentMessageBuilder.tryDeleteDocument+ id);
        try {
            Document document = documentRepository.findById(id).get();
                documentRepository.deleteById(id);
                String message =DocumentMessageBuilder.deleteSuccess;
                logger.info(message);
                return message;
            }
         catch (Exception e) {
            throw e;
        }
       }

    }






























