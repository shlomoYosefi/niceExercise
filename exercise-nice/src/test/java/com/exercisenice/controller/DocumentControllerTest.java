package com.exercisenice.controller;
import com.exercisenice.controllers.DocumentController;
import com.exercisenice.messageBuilder.DocumentMessageBuilder;
import com.exercisenice.models.Document;
import com.exercisenice.repositories.DocumentRepository;
import com.exercisenice.services.DocumentService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@SpringBootTest
class DocumentControllerTest {

    @Autowired
    private DocumentController documentController;
    @MockBean
    private DocumentRepository documentRepository;
    @InjectMocks
    private DocumentService documentService;


    final Long id =1L;
    public Document document1 = new Document(id,"test1","test1",LocalDateTime.now());
    public Document document2 = new Document(2L,"test2","test2",LocalDateTime.now());



    @Test
    @DisplayName("Test add document to mock data")
    void addDocument() {
        //given
        Document document = document1;
        //when
        assertEquals(200,documentController.addDocument(document).getStatusCodeValue());
        assertEquals("The document was successfully added",documentController.addDocument(document).getBody());
    }


    @Test
    @DisplayName("Test gat all documents from mock data")
    void getAllDocuments() throws Exception {
        //given
        Document document1 = this.document1;
        Document document2 = this.document2;
        //when
        when(documentRepository.findAll()).thenReturn(Stream
        .of(document1,document2).collect(Collectors.toList()));
        //then
        assertEquals(2,documentService.getDocuments().size());
        assertEquals(200,documentController.getAllDocument().getStatusCodeValue());
    }

    @Test
    @DisplayName("Test get document by id from mock data")
    void getDocumentByIdTest() throws Exception {
        //given
        Document document = document1;
        //when
        when(documentRepository.findById(id)).thenReturn(Optional.of(document));
        //then
        assertEquals(document,documentController.getDocumentById(id).getBody());
    }



    @Test
    @DisplayName("Test get document by id from mock data with exception PersistenceException and make retry")
    void getDocumentByIdWithExceptionNoSuchElement(){
        when(documentRepository.findById(id)).thenThrow(PersistenceException.class);
        assertThrows(PersistenceException.class,
                ()->{
                    documentService.getDocumentById(id);
                });
        assertEquals(500,documentController.getDocumentById(id).getStatusCodeValue());
    }


    @Test
    @DisplayName("Test delete document by id from mock data")
    void deleteDocument() throws SQLException {
        Document document = document1;
        when(documentRepository.findById(id)).thenReturn(Optional.ofNullable(document));
        documentController.deleteDocument(id);
        verify(documentRepository, times(1)).deleteById(id);
        assertEquals(200,documentController.deleteDocument(id).getStatusCodeValue());
    }

    @Test
    @DisplayName("Test delete document by id from mock data with exception NoSuchElementException")
    void deleteDocumentByIdWithExceptionNoSuchElement(){
        when(documentRepository.findById(id)).thenThrow(NoSuchElementException.class);
        documentController.deleteDocument(id);
        assertThrows(NoSuchElementException.class,
                ()->{
                    documentService.deleteDocument(id);
                });
        assertEquals(404,documentController.deleteDocument(id).getStatusCodeValue());
    }



    @Test
    @DisplayName("Test update document in mock data")
    void updateDocument() throws SQLException {
        //given
        Document document = document1;
        when(documentRepository.findById(id)).thenReturn(Optional.of(document));
        documentService.updateDocument(document,id);
        verify(documentRepository, times(1)).save(document);
        assertEquals(200,documentController.updateDocument(id,document).getStatusCodeValue());
    }

    @Test
    @DisplayName("Test update document by id from mock data with exception NoSuchElementException")
    void updateDocumentByIdWithExceptionNoSuchElement(){
        Document document = document1;
        when(documentRepository.findById(id)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class,
                ()->{
                    documentService.updateDocument(document,id);
                });
        assertEquals(404,documentController.updateDocument(id,document).getStatusCodeValue());
    }



    @BeforeEach
    void setUp(){
        documentService = new DocumentService(documentRepository);
    }
}