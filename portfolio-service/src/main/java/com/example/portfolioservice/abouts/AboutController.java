package com.example.portfolioservice.abouts;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/abouts")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public Flux<AboutResponse> getAllAbouts() {
        return aboutService.getAllAbouts();
    }

    @GetMapping("/{id}")
    public Mono<AboutResponse> getAboutById(@PathVariable String id) {
        return aboutService.getAboutById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AboutResponse> createAbout(@Valid @RequestBody AboutRequest aboutRequest) {
        return aboutService.createAbout(aboutRequest);
    }

    @PutMapping("/{id}")
    public Mono<AboutResponse> updateAbout(@PathVariable(value = "id") String id,
                                           @Valid @RequestBody AboutRequest aboutRequest) {
        return aboutService.updateAbout(id, aboutRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAbout(@PathVariable(value = "id") String id) {
        return aboutService.deleteAbout(id);
    }
}