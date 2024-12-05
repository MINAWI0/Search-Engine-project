package org.example.enginsearchv4.service;

import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.Repo.DocumentTermRepository;
import org.example.enginsearchv4.Repo.TermRepository;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.model.DocumentTerm;
import org.example.enginsearchv4.model.Term;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final DocumentRepository documentRepository;
    private final TermRepository termRepository;
    private final DocumentTermRepository documentTermRepository;

    public List<Document> search(String query, int limit) {
        // Create query vector
        Map<String, Double> queryVector = createQueryVector(query);

        // Calculate cosine similarity between query vector and document vectors
        Map<Document, Double> similarities = calculateSimilarities(queryVector);

        // Sort documents by similarity and return top N results
        return similarities.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<String, Double> createQueryVector(String query) {
        Map<String, Double> queryVector = new HashMap<>();
        String[] terms = query.split("\\s+");
        for (String term : terms) {
            queryVector.merge(term, 1.0, Double::sum);
        }

        // Normalize query vector
        double norm = Math.sqrt(queryVector.values().stream().mapToDouble(Double::doubleValue).sum());
        queryVector.replaceAll((term, weight) -> weight / norm);
        return queryVector;
    }

    private Map<Document, Double> calculateSimilarities(Map<String, Double> queryVector) {
        Map<Document, Double> similarities = new HashMap<>();

        // Get all documents that contain at least one term from the query
        Set<Document> documents = documentTermRepository.findByTermIn(queryVector.keySet())
                .stream()
                .map(DocumentTerm::getDocument)
                .collect(Collectors.toSet());

        for (Document document : documents) {
            // Get document vector
            Map<String, Double> documentVector = getDocumentVector(document);

            // Calculate dot product of query vector and document vector
            double dotProduct = queryVector.entrySet().stream()
                    .mapToDouble(entry -> entry.getValue() * documentVector.getOrDefault(entry.getKey(), 0.0))
                    .sum();

            // Calculate cosine similarity
            double similarity = dotProduct / Math.sqrt(documentVector.values().stream().mapToDouble(Double::doubleValue).sum());
            similarities.put(document, similarity);
        }
        return similarities;
    }

    private Map<String, Double> getDocumentVector(Document document) {
        Map<String, Double> documentVector = new HashMap<>();
        List<DocumentTerm> documentTerms = documentTermRepository.findByDocument(document);
        for (DocumentTerm documentTerm : documentTerms) {
            documentVector.put(documentTerm.getTerm().getTerm(), documentTerm.getTfIdfScore());
        }
        return documentVector;
    }
}