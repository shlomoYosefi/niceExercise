package com.exercisenice.services;

import com.exercisenice.controllers.DocumentController;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import org.hibernate.type.LocalDateTimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DocumentServiceTest {

    private Long idDocumentTest;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentController documentController;

    @Test
    void addDocumentTest() {
        //given
        Document document = new Document("9999","yosefi");
        //when
        ResponseEntity response = documentController.addDocument(document);
        //then
        assertEquals(response.getStatusCodeValue(),200);
    }

    @Test
    @Disabled
    void addDocumentTestWithEmptyParameters() {
        //given
        Document document = new Document();
        //when
        ResponseEntity response = documentController.addDocument(document);
        //then
        assertEquals(response.getStatusCodeValue(),400);
    }

//    @Test
//    void getDocumentByIdTest() throws IOException {
//        //when
//        ResponseEntity response = documentController.getDocumentById(idDocumentTest);
//        //then
//        assertEquals(response.getStatusCodeValue(),200);
//    }
//
//    @Test
//    void getDocumentsTest() throws IOException {
//        ResponseEntity response = documentController.getAllDocument();
//        assertEquals(response.getStatusCodeValue(),200);
//    }
//
//    @Test
//    void updateDocumentTest() throws IOException {
//        //given
//        Document document = new Document(idDocumentTest,"test","osem",LocalDateTime.now());
//        //when
//        ResponseEntity response = documentController.updateDocument(idDocumentTest,document);
//        //then
//        assertEquals(response.getStatusCodeValue(),200);
//    }
//
//    @Test
//    void deleteDocument() {
//        ResponseEntity response =documentController.deleteDocument(idDocumentTest);
//        Boolean document = documentRepository.existsById(idDocumentTest);
//        assertEquals(response.getStatusCodeValue(),200);
//        assertTrue(!document);
//    }


    @BeforeEach
    void setUp(){
        documentRepository.save(new Document("5555","yosefi"));
        idDocumentTest = documentRepository.findTopByOrderByIdDesc().getId();
        System.out.println(" iddddd :"+idDocumentTest);
    }
}