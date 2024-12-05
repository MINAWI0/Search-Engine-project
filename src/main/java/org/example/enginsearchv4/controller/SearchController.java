package org.example.enginsearchv4.controller;

import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<Document>> search(@RequestParam("query") String query, @RequestParam("limit") int limit) {
        List<Document> results = searchService.search(query, limit);
        return ResponseEntity.ok(results);
    }
}