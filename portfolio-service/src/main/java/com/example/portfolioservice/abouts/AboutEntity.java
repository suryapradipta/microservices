package com.example.portfolioservice.abouts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "abouts")
public class AboutEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    @CreatedBy
    String createdBy;
    @CreatedDate
    Instant createdAt;
    @LastModifiedBy
    String updatedBy;
    @LastModifiedDate
    Instant updatedAt;
}