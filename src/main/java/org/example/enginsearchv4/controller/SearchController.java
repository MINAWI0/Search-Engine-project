package org.example.enginsearchv4.controller;

import lombok.RequiredArgsConstructor;
import org.example.enginsearchv4.Repo.DocumentRepository;
import org.example.enginsearchv4.dto.DocumentDTO;
import org.example.enginsearchv4.model.Document;
import org.example.enginsearchv4.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/")
    public String showSearchForm() {
        return "search";
    }

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