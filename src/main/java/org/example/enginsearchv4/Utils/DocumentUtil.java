package org.example.enginsearchv4.Utils;

import org.apache.tika.exception.TikaException;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DocumentUtil {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TikaExtractionUtils tikaExtractionUtils;  // Inject TikaExtractionUtils class for extracting text and metadata

    /**
     * Processes and saves documents from a given directory.
     *
     * @param directoryPath The path to the directory containing the PDF files.
     * @throws IOException If an I/O error occurs during the process.
     */
    public void saveDocumentsFromDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        // Check if the directory exists and is valid
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
        }

        // Get all PDF files in the directory
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (files == null || files.length == 0) {
            throw new IllegalStateException("No PDF files found in the directory: " + directoryPath);
        }

        // Process each PDF file
        for (File documentFile : files) {
            try {
                // Extract only the content (without metadata)
                String content = tikaExtractionUtils.extractText(documentFile); // This will give you only the content without metadata

                // If the document already exists, skip it
                if (documentRepository.existsByTitleAndDocLength(documentFile.getName(), content.length())) {
                    System.out.println("Document already exists: " + documentFile.getName());
                    continue;
                }

                // If no content is extracted, skip this document
                if (content.isEmpty()) {
                    System.out.println("No content extracted from: " + documentFile.getName());
                    continue;
                }

                // Create a new Document object and set its properties
                Document document = new Document();
                document.setTitle(documentFile.getName());
                document.setContent(content); // Store content without metadata
                document.setDocLength(content.length());

                // Save the document to the repository
                documentRepository.save(document);
                System.out.println("Document saved successfully: " + documentFile.getName());
            } catch (IOException | TikaException e) {
                System.err.println("Error processing file " + documentFile.getName() + ": " + e.getMessage());
            }
        }
    }
}
