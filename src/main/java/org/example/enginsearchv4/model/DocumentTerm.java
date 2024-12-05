package org.example.enginsearchv4.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_terms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary Key for the document-term pair

    @ManyToOne
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;  // The associated document
    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;  // The associated term
    @Column(name = "tf_score", nullable = false)
    private Double tfScore;  // Term Frequency score for this document-term pair
    @Column(name = "idf_score", nullable = false)
    private Double idfScore;  // Inverse Document Frequency score for this term
    @Column(name = "tf_idf_score", nullable = false)
    private Double tfIdfScore;  // TF-IDF score for this document-term pair

}