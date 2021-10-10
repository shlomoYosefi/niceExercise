package com.exercisenice.repositories;

import com.exercisenice.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    Document findTopByOrderByIdDesc();
    List<Document> findByLabel(String label);
}
