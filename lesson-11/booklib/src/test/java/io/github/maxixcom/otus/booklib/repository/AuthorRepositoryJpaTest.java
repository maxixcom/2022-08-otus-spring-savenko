package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    void shouldReturnAllGenres() {
        List<Author> authorList = authorRepositoryJpa.findAll();

        Assertions.assertThat(authorList).hasSizeGreaterThan(0);
    }
}