package com.exercisenice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table
public class Document {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "It must be label")
    @Size(min = 4,max = 10,message = "label should be between 1 and 10")
    private String label;
    @NotBlank(message = "It must be documentName")
    @Size(min = 1,max = 15,message = "documentName should be between 1 and 15")
    private String documentName;
    private LocalDateTime creationDate;

    public Document() {
    }

    public Document(Long idDocumentTest, String label, String documentName, LocalDateTime localDateTime) {
        this.id = idDocumentTest;
        this.label = label;
        this.documentName = documentName;
        this.creationDate = localDateTime;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }



    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", documentName='" + documentName + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
