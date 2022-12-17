package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class GenreRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldReturnAllGenres() {
        List<Genre> genreList = genreRepository.findAll();

        Assertions.assertThat(genreList).hasSizeGreaterThan(0);
    }

    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = em.find(Genre.class, 1L);
        Optional<Genre> genreOptional = genreRepository.findById(1L);

        Assertions.assertThat(genreOptional)
                .isPresent()
                .containsInstanceOf(Genre.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b).usingRecursiveComparison().isEqualTo(expectedGenre);
                });
    }
}