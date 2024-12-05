package org.example.enginsearchv4.service;


import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.Repo.DocumentTermRepository;
import org.example.enginsearchv4.Repo.TermRepository;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.model.DocumentTerm;
import org.example.enginsearchv4.model.Term;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IndexService {
    private final DocumentRepository documentRepository;
    private final TermRepository termRepository;
    private final DocumentTermRepository documentTermRepository;


    private void saveTerms(Set<String> terms) {
        terms.forEach(termText -> {
            if (!termRepository.existsByTerm(termText)) {
                Term term = new Term();
                term.setTerm(termText);
                term.setDocumentCount(1);
                term.setIdfScore(0.0); // Initial IDF score
                termRepository.save(term);
            }
        });
    }

    public void indexDocument(Document document) {
        Map<String, Double> documentVector = createDocumentVector(document);
        Map<String, Double> tfScores = calculateTF(documentVector, document.getDocLength());

        // First, save all terms with initial IDF scores
        saveTerms(documentVector.keySet());

        // Then update IDF scores
        updateIDF();

        // Finally, save document vector with updated scores
        saveDocumentVector(document, tfScores);
    }
    private Map<String, Double> createDocumentVector(Document document) {
        Map<String, Double> vector = new HashMap<>();
        String[] terms = document.getContent().split("\\s+");
        for (String term : terms) {
            vector.merge(term, 1.0, Double::sum);
        }
        return vector;
    }

    private Map<String, Double> calculateTF(Map<String, Double> termFrequencies, int docLength) {
        Map<String, Double> tfScores = new HashMap<>();
        termFrequencies.forEach((term, frequency) -> {
            double tf = frequency / docLength;
            tfScores.put(term, tf);
        });
        return tfScores;
    }

    private void updateIDF() {
        long totalDocuments = documentRepository.count();
        termRepository.findAll().forEach(term -> {
            int documentFrequency = term.getDocumentCount();
            // Add smoothing factor
            double idf = Math.log10((double) (totalDocuments + 1) / (documentFrequency + 1));
            term.setIdfScore(idf);
            termRepository.save(term);
        });
    }

    private void saveDocumentVector(Document document, Map<String, Double> tfScores) {
        tfScores.forEach((termText, tfScore) -> {
            Term term = termRepository.findByTerm(termText)
                    .orElseGet(() -> {
                        Term newTerm = new Term();
                        newTerm.setTerm(termText);
                        newTerm.setDocumentCount(1);
                        return termRepository.save(newTerm);
                    });
            double tfIdfScore = tfScore * term.getIdfScore();
            DocumentTerm documentTerm = new DocumentTerm();
            documentTerm.setDocument(document);
            documentTerm.setTerm(term);
            documentTerm.setTfScore(tfScore);
            documentTerm.setIdfScore(term.getIdfScore());
            documentTerm.setTfIdfScore(tfIdfScore);
            documentTermRepository.save(documentTerm);
        });
    }

}
