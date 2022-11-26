package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.GenreDto;
import reactor.core.publisher.Flux;

public interface GenreService {
    Flux<GenreDto> getAllGenres();
}
