package org.example.enginsearchv4.Repo;

import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.model.DocumentTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DocumentTermRepository extends JpaRepository<DocumentTerm, Long> {
    List<DocumentTerm> findByDocument(Document document);

    @Query("SELECT dt FROM DocumentTerm dt WHERE dt.term.term IN :terms")
    List<DocumentTerm> findByTermIn(@Param("terms") Collection<String> terms);

}
