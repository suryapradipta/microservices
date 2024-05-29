package com.example.portfolioservice.abouts;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutRequest {
    private String title;
    private String description;
    private String imageUrl;
}