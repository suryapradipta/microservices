package com.example.portfolioservice.abouts;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutResponse {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}