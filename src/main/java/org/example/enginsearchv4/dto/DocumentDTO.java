package org.example.enginsearchv4.dto;

import org.example.enginsearchv4.model.Document;

// Create a DTO
public record DocumentDTO(Long id, String title , String path ,  String thumbnailPath) {
    public static DocumentDTO fromDocument(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getTitle(),
                document.getFilePath(),
                document.getThumbnailPath()
        );
    }
}
