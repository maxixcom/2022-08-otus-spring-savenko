package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = Genre.builder()
                .id(1L)
                .title("Ужасы")
                .build();

        Optional<Genre> genreOptional = genreDao.findById(1);
        Assertions.assertThat(genreOptional)
                .isPresent()
                .containsInstanceOf(Genre.class)
                .hasValue(expectedGenre);
    }

    @Test
    void shouldReturnAllGenres() {
        List<Genre> genreList = genreDao.findAll();
        Assertions.assertThat(genreList).size().isEqualTo(10);
    }

    @Test
    void shouldInsertGenre() {
        Genre author = Genre.builder()
                .title("XXX")
                .build();

        long id = genreDao.insert(author);

        Assertions.assertThat(id).isEqualTo(100);

        Optional<Genre> genreOptional = genreDao.findById(100);
        Assertions.assertThat(genreOptional)
                .isPresent()
                .containsInstanceOf(Genre.class)
                .hasValueSatisfying(a -> {
                    Assertions.assertThat(a.getTitle()).isEqualTo("XXX");
                });
    }
}