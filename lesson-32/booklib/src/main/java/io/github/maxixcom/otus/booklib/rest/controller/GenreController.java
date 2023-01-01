package io.github.maxixcom.otus.booklib.rest.controller;

import io.github.maxixcom.otus.booklib.dto.GenreDto;
import io.github.maxixcom.otus.booklib.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/api/genre")
    List<GenreDto> listGenres() {
        return genreService.getAllGenres();
    }
}
