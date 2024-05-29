package com.example.portfolioservice.abouts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AboutService {

    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public Flux<AboutResponse> getAllAbouts() {
        return aboutRepository.findAll()
                .map(this::entityToResponse);
    }

    public Mono<AboutResponse> createAbout(AboutRequest aboutRequest) {
        return aboutRepository.save(requestToEntity(aboutRequest))
                .map(this::entityToResponse);
    }

    public Mono<AboutResponse> updateAbout(String id, AboutRequest aboutRequest) {
        return aboutRepository.findById(id)
                .flatMap(existingAbout -> {
                    existingAbout.setTitle(aboutRequest.getTitle());
                    existingAbout.setDescription(aboutRequest.getDescription());
                    existingAbout.setImageUrl(aboutRequest.getImageUrl());
                    return aboutRepository.save(existingAbout);
                })
                .map(this::entityToResponse);
    }

    public Mono<ResponseEntity<Void>> deleteAbout(String id) {
        return aboutRepository.findById(id)
                .flatMap(existingAbout ->
                        aboutRepository.delete(existingAbout)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<AboutResponse> getAboutById(String id) {
        return aboutRepository.findById(id)
                .map(this::entityToResponse);
    }

    private AboutEntity requestToEntity(AboutRequest request) {
        return AboutEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }

    private AboutResponse entityToResponse(AboutEntity entity) {
        return AboutResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedBy(entity.getUpdatedBy())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}