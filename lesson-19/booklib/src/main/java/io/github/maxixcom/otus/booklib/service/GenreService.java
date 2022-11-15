package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAllGenres();
}
