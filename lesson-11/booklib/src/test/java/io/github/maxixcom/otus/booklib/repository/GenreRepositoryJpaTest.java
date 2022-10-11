package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    void shouldReturnAllGenres() {
        List<Genre> genreList = genreRepositoryJpa.findAll();

        Assertions.assertThat(genreList).hasSizeGreaterThan(0);
    }
}