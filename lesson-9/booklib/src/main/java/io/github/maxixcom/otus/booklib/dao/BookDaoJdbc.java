package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book.BookBuilder bookBuilder = Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"));

            Long genreId = rs.getLong("genre_id");
            if (!rs.wasNull()) {
                Genre genre = Genre.builder()
                        .id(genreId)
                        .title(rs.getString("genre_title"))
                        .build();

                bookBuilder.genre(genre);
            }

            Long authorId = rs.getLong("author_id");
            if (!rs.wasNull()) {
                Author author = Author.builder()
                        .id(authorId)
                        .name(rs.getString("author_name"))
                        .build();

                bookBuilder.author(author);
            }

            return bookBuilder.build();
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT " +
                "b.id, " +
                "b.title, " +
                "b.author_id, " +
                "b.genre_id, " +
                "a.name as author_name, " +
                "g.title as genre_title " +
                "FROM book as b " +
                "LEFT JOIN author as a ON a.id=b.author_id " +
                "LEFT JOIN genre as g ON g.id=genre_id";

        return jdbc.query(sql, new BookRowMapper());
    }
}
