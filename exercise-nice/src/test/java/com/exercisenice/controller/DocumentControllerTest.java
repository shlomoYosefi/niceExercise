package com.exercisenice.controller;

import com.exercisenice.controllers.DocumentController;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import com.exercisenice.services.DocumentService;
import javassist.tools.web.BadHttpRequest;
import org.hibernate.type.LocalDateTimeType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @Mock
    private DocumentRepository documentRepository;
    private DocumentService documentService;

    @Test
    void addDocument(){
        //when
        Document document = new Document("shlomo","yosefi",LocalDateTime.now());
        //then
        documentService.addDocument(document);
        //then
        ArgumentCaptor<Document> documentArgumentCaptor = ArgumentCaptor.forClass(Document.class);
        verify(documentRepository).save(documentArgumentCaptor.capture());
        Document capturedDocument = documentArgumentCaptor.getValue();
        assertThat(capturedDocument).isEqualTo(document);
    }

    @Test
    void getAllDocuments(){
        //when
        documentService.getDocuments();
        //then
        verify(documentRepository).findAll();
    }

    @Test
    void getDocumentByIdTest() throws IOException, BadHttpRequest {
        Document document = new Document("shlomo","yosefi",LocalDateTime.now());
        documentService.addDocument(document);
        //when
//        Document getDocument = documentService.getDocumentById(1L);

        System.out.println(documentRepository.findAll());
//        when(documentService.getDocumentById(1L)).thenReturn(document);
        //then
    }

    @BeforeEach
    void setUp(){
        documentService = new DocumentService(documentRepository);
    }




















//    private Long idDocumentTest;
//    @Autowired
//    private DocumentRepository documentRepository;
//    @Autowired
//    private DocumentController documentController;
//
//    @Test
//    @DisplayName("Test add document to DB")
//    void addDocumentTest() {
//        //given
//        Document document = new Document("9999","yosefi");
//        //when
//        ResponseEntity response = documentController.addDocument(document);
//        //then
//        assertEquals(response.getStatusCodeValue(),200);
//    }
//
//
//
//    @Test
//    void getDocumentByIdTest() throws IOException, BadHttpRequest {
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
//    void getDocumentTestWithIdErroneous() throws BadHttpRequest {
//        //given
//        Long id = 0L;
//        //when
//        ResponseEntity response = documentController.getDocumentById(id);
//        //then
//        assertEquals(response.getStatusCodeValue(), 404);
//    }
//
//    @Test
//    void updateDocumentTest() throws IOException {
//        //given
//        Document document = new Document();
//        document.setCreationDate(LocalDateTime.now());
//        document.setDocumentName("update");
//        document.setId(idDocumentTest);
//        document.setLabel("update");
//        //when
//        ResponseEntity response = documentController.updateDocument(idDocumentTest,document);
//        //then
//        assertEquals(response.getStatusCodeValue(),200);
//    }
//
//
//    @Test
//    void updateDocumentTestWithIdErroneous() throws BadHttpRequest {
//        //given
//        Long id = 0L;
//        Document document = new Document();
//        document.setCreationDate(LocalDateTime.now());
//        document.setDocumentName("update");
//        document.setId(id);
//        document.setLabel("update");
//        //when
//        ResponseEntity response = documentController.updateDocument(id,document);
//        //then
//        assertEquals(response.getStatusCodeValue(), 404);
//    }
//
//    @Test
////    @Disabled
//    void deleteDocument() {
//        ResponseEntity response =documentController.deleteDocument(idDocumentTest);
//        Boolean document = documentRepository.existsById(idDocumentTest);
//        assertEquals(response.getStatusCodeValue(),200);
//        assertTrue(!document);
//    }
//
//
//    @BeforeEach
//    void addRowTestToTable(){
//        idDocumentTest = documentRepository.findTopByOrderByIdDesc().getId();
//    }
}