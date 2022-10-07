package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    void shouldReturnAllAuthors() {
        List<Author> authorList = authorDao.findAll();
        Assertions.assertThat(authorList).size().isEqualTo(10);
    }
}