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
    void shouldReturnAllGenres() {
        List<Genre> genreList = genreDao.findAll();
        Assertions.assertThat(genreList).size().isEqualTo(10);
    }
}