package io.github.maxixcom.otus.booklib.rest.controller;

import io.github.maxixcom.otus.booklib.dto.AuthorDto;
import io.github.maxixcom.otus.booklib.service.AuthorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @CircuitBreaker(name = "listAuthors", fallbackMethod = "fallbackListAuthors")
    @GetMapping("/api/author")
    List<AuthorDto> listAuthors() {
        return authorService.getAllAuthors();
    }

    List<AuthorDto> fallbackListAuthors(Exception exception) {
        return Collections.emptyList();
    }
}
