package org.example.enginsearchv4.controller;


import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.Utils.DocumentUtil;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.service.IndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {
    private final DocumentUtil documentUtil;
    private final IndexService indexService;
    private final DocumentRepository documentRepository;

    @PostMapping
    public ResponseEntity<?> indexDirectory() {
        Map<String, Object> response = new HashMap<>();
        for (Document document : documentRepository.findAll()) {
            indexService.indexDocument(document);
        }
        response.put("status", "success");
        response.put("message", "Documents indexed successfully");
        return ResponseEntity.ok(response);
    }
}
