package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    void shouldReturnAllAuthors() {
        List<Author> authorList = authorRepositoryJpa.findAll();

        Assertions.assertThat(authorList).hasSizeGreaterThan(0);
    }

    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = em.find(Author.class, 1L);
        Optional<Author> authorOptional = authorRepositoryJpa.findById(1L);

        Assertions.assertThat(authorOptional)
                .isPresent()
                .containsInstanceOf(Author.class)
                .hasValueSatisfying(b -> {
                    Assertions.assertThat(b).usingRecursiveComparison().isEqualTo(expectedAuthor);
                });
    }
}