package org.example.enginsearchv4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary Key for the document
    @Column(name = "title", nullable = false)
    private String title;  // Title of the document
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;  // Full content of the document
    @Column(name = "doc_length", nullable = false)
    private Integer docLength;  // Total number of words/terms in the document
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "thumbnail_path")
    private String thumbnailPath;

}