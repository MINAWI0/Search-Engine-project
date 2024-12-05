package org.example.enginsearchv4.config;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public Tika tika() {
        return new Tika();
    }
    @Bean
    public EnglishAnalyzer englishAnalyzer() throws IOException {
        // Create a CharArraySet from custom stop words file
        CharArraySet customStopWords = loadStopWords("stop-words.txt");
        return new EnglishAnalyzer(customStopWords);
    }

    private CharArraySet loadStopWords(String filePath) throws IOException {
        Set<String> stopWords = new HashSet<>();

        // Read from classpath resource
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty()) {
                    stopWords.add(line);
                }
            }
        }

        // Convert to CharArraySet (true parameter makes it case-insensitive)
        return new CharArraySet(stopWords, true);
    }
}