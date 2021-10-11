package com.exercisenice.controller;

import com.exercisenice.controllers.DocumentController;
import com.exercisenice.messageBuilder.DocumentMessageBuilder;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import com.exercisenice.services.DocumentService;
import javassist.tools.web.BadHttpRequest;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    private DocumentController documentController;
    @MockBean
    private DocumentRepository documentRepository;
    @InjectMocks
    private DocumentService documentService;
    Document document1 = new Document(1L,"test1","test1",LocalDateTime.now());
    Document document2 = new Document(2L,"test2","test2",LocalDateTime.now());

//

    @Test
    void addDocument() throws SQLException {
        //given
        Document document = document1;
        //when
        when(documentRepository.save(document)).thenReturn(document);
        documentController.addDocument(document);
        //then
        assertEquals(document,documentRepository.save(document));
    }

//    @Test
//    void addDocumentByIdWithExceptionSQLException() throws SQLException {
//        Document document = document1;
//        when(documentRepository.save(document)).thenAnswer(DocumentMessageBuilder.);
//        assertEquals(DocumentMessageBuilder.faultInTheDataServer,documentController.addDocument(document).getBody());
//    }

    @Test
    void getAllDocuments() throws Exception {
        //given
        Document document1 = this.document1;
        Document document2 = this.document2;
        //when
        when(documentController.getAllDocument().getBody()).thenReturn(Stream
        .of(document1,document2).collect(Collectors.toList()));
        //then
        assertEquals(2,documentService.getDocuments().size());
    }

    @Test
    void getDocumentByIdTest() throws Exception {
        Document document = document1;

//        Optional<Document> document = Optional.of(new Document("shlomo","yosefi",LocalDateTime.now()));
//        given(documentRepository.findById(Mockito.anyLong())).willReturn(document);
//        Document returnedObject = documentService.getDocumentById(1L);
//        Mockito.verify(documentRepository).findById(Mockito.anyLong());
//        assertNotNull(returnedObject);
//        assertEquals(returnedObject.getLabel(),"shlomo");
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        documentController.getDocumentById(1L);
        verify(documentRepository, times(1)).findById(1L);
    }

    @Test
    void getDocumentByIdWithExceptionNoSuchElement(){
        String result = (String) documentController.getDocumentById(100L).getBody();
        assertEquals("document does not exists",result);
    }


    @Test
    void deleteDocument() throws SQLException {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(new Document()));
        documentController.deleteDocument(1L);
        verify(documentRepository, times(1)).deleteById(1L);
    }


    @Test
    void deleteDocumentByIdWithExceptionNoSuchElement(){
        String result = (String) documentController.deleteDocument(100L).getBody();
        assertEquals("document does not exists",result);
    }

    @Test
    void updateDocument() throws SQLException {
        //given
        Document document = document1;
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        documentService.updateDocument(document,1L);
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void updateDocumentByIdWithExceptionNoSuchElement(){
        Document document = document1;
        String result = (String) documentController.updateDocument(100L,document).getBody();
        assertEquals("document does not exists",result);
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