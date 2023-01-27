package io.github.maxixcom.otus.booklib.rest.controller;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.service.GenreService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @CircuitBreaker(name = "listGenres", fallbackMethod = "fallbackListGenres")
    @GetMapping("/api/genre")
    List<GenreDto> listGenres() {
        return genreService.getAllGenres();
    }

    List<AuthorDto> fallbackListGenres(Exception exception) {
        return Collections.emptyList();
    }
}
