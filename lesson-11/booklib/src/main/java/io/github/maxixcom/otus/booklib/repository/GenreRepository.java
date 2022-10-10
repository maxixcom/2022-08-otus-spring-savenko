package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();
}
