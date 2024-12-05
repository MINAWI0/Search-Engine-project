package org.example.enginsearchv4.Utils;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TikaExtractionUtils {

    private final Tika tika;
    private final EnglishAnalyzer englishAnalyzer;

    public String extractText(File file) throws IOException, TikaException {
        String extractedText = tika.parseToString(file);
        String cleanedText = cleanText(extractedText);
        return removeStopWords(cleanedText);
    }

    private String cleanText(String text) {
        return text
                .replaceAll("\\s+", " ")  // Replace multiple spaces with a single space
                .trim();                   // Trim leading/trailing spaces
    }

    private String removeStopWords(String text) throws IOException {
        TokenStream tokenStream = englishAnalyzer.tokenStream(null, new StringReader(text));

        List<String> tokens = new ArrayList<>();

        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                tokens.add(charTermAttribute.toString());
            }
            tokenStream.end();
        } finally {
            tokenStream.close();
        }
        return String.join(" ", tokens).toLowerCase();
    }
}