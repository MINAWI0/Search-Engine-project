package org.example.enginsearchv4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary Key for the term
    @Column(name = "term", nullable = false, unique = true , columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String term;  // The term/word
    @Column(name = "idf_score", nullable = false)
    private Double idfScore;  // Inverse Document Frequency for the term
    @Column(name = "document_count", nullable = false)
    private Integer documentCount;  // The number of documents containing the term

}