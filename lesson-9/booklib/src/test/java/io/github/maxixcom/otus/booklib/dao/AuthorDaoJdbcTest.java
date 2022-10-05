package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = Author.builder()
                .id(10L)
                .name("Герберт Уэллс")
                .build();

        Optional<Author> authorOptional = authorDao.findById(10);
        Assertions.assertThat(authorOptional)
                .isPresent()
                .containsInstanceOf(Author.class)
                .hasValue(expectedAuthor);
    }

    @Test
    void shouldReturnAllAuthors() {
        List<Author> authorList = authorDao.findAll();
        Assertions.assertThat(authorList).size().isEqualTo(10);
    }

    @Test
    void shouldInsertAuthor() {
        Author author = Author.builder()
                .name("XXX")
                .build();

        long id = authorDao.insert(author);

        Assertions.assertThat(id).isEqualTo(100);

        Optional<Author> authorOptional = authorDao.findById(100);
        Assertions.assertThat(authorOptional)
                .isPresent()
                .containsInstanceOf(Author.class)
                .hasValueSatisfying( a -> {
                    Assertions.assertThat(a.getName()).isEqualTo("XXX");
                });
    }
}