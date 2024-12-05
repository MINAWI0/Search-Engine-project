package org.example.enginsearchv4.Repo;

import org.example.enginsearchv4.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    boolean existsByTitleAndDocLength(String title ,  Integer length);
}