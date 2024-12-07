package org.example.enginsearchv4.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.dto.DocumentDTO;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.service.SearchService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RestController
@RequiredArgsConstructor
@Controller
public class SearchController {

    private final SearchService searchService;
    private final DocumentRepository documentRepository;
    @GetMapping("/")
    public String showSearchForm() {
        return "search";
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<DocumentDTO>> search(@RequestParam("query") String query,
//                                                    @RequestParam("limit") int limit) {
//        List<Document> results = searchService.search(query, limit);
//        List<DocumentDTO> dtos = results.stream()
//                .map(DocumentDTO::fromDocument)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
@GetMapping("/search")
public String search(@RequestParam("query") String query,
                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                     Model model) {
    List<Document> results = searchService.search(query, limit);
    List<DocumentDTO> dtos = results.stream()
            .map(DocumentDTO::fromDocument)
            .collect(Collectors.toList());
    model.addAttribute("results", dtos);
    return "search";
}

}