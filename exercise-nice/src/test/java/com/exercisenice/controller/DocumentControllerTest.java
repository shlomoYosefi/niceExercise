package com.exercisenice.controller;

import com.exercisenice.controllers.DocumentController;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import com.exercisenice.services.DocumentService;
import javassist.tools.web.BadHttpRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @MockBean
    private DocumentRepository documentRepository;
    private DocumentService documentService;

    @Test
    void addDocument() throws SQLException {
        //when
        Document document = new Document("shlomo","yosefi",LocalDateTime.now());
//        when(documentRepository.save(document)).thenReturn(document);
//        assertEquals(document,documentRepository.save(document));
        //then
        documentService.addDocument(document);
        //then
        ArgumentCaptor<Document> documentArgumentCaptor = ArgumentCaptor.forClass(Document.class);
        verify(documentRepository).save(documentArgumentCaptor.capture());
        Document capturedDocument = documentArgumentCaptor.getValue();
        assertThat(capturedDocument).isEqualTo(document);
    }

    @Test
    void getAllDocuments() throws SQLException {
        Document document1 = new Document(1L,"shlomo","yosefi",LocalDateTime.now());
        Document document2 = new Document(2L,"ravtech","avratech",LocalDateTime.now());
        when(documentService.getDocuments()).thenReturn(Stream
        .of(document1,document2).collect(Collectors.toList()));
        assertEquals(2,documentService.getDocuments().size());
    }

    @Test
    void getDocumentByIdTest() throws  SQLException {
//        Document document = new Document(1L,"shlomo","yosefi",LocalDateTime.now());
//        when(documentRepository.findByLabel("shlomo")).thenReturn( Stream
//        .of(document).collect(Collectors.toList()));
//        assertEquals(1,documentRepository.findAll().size());        //when
    }

    @Test
    void deleteDocument(){
        Document document = new Document(1L,"shlomo","yosefi",LocalDateTime.now());
        documentRepository.delete(document);
        verify(documentRepository,times(1)).delete(document);
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