package com.example.portfolioservice.abouts;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AboutRepository extends ReactiveCrudRepository<AboutEntity, String> {
}
