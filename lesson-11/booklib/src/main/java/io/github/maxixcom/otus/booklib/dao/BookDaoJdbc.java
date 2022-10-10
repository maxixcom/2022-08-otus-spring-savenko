package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT " +
                "b.id, " +
                "b.title, " +
                "b.author_id, " +
                "b.genre_id, " +
                "a.name as author_name, " +
                "g.title as genre_title " +
                "FROM book as b " +
                "LEFT JOIN author as a ON a.id=b.author_id " +
                "LEFT JOIN genre as g ON g.id=genre_id " +
                "WHERE " +
                "b.id=:id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            Book book = jdbc.queryForObject(sql, parameterSource, new BookRowMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            // Just return empty optional
        }
        return Optional.empty();
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

    @Override
    public void deleteByIds(Set<Long> ids) {
        String sql = "DELETE FROM book WHERE id in (:ids)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ids", ids);

        jdbc.update(sql, parameterSource);
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE book SET " +
                "title=:title, " +
                "author_id=:authorId, " +
                "genre_id=:genreId " +
                "WHERE id=:id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())

                .addValue("authorId",
                        Optional.ofNullable(book.getAuthor())
                                .map(Author::getId)
                                .orElse(null)
                )

                .addValue("genreId",
                        Optional.ofNullable(book.getGenre())
                                .map(Genre::getId)
                                .orElse(null)
                );

        jdbc.update(sql, parameterSource);
    }

    @Override
    public long insert(Book book) {
        String sql = "INSERT INTO book " +
                "(title, author_id, genre_id) " +
                "VALUES " +
                "(:title,:authorId,:genreId)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("title", book.getTitle())

                .addValue("authorId",
                        Optional.ofNullable(book.getAuthor())
                                .map(Author::getId)
                                .orElse(null)
                )

                .addValue("genreId",
                        Optional.ofNullable(book.getGenre())
                                .map(Genre::getId)
                                .orElse(null)
                );

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(sql, parameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book.BookBuilder bookBuilder = Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"));

            long genreId = rs.getLong("genre_id");
            if (!rs.wasNull()) {
                Genre genre = Genre.builder()
                        .id(genreId)
                        .title(rs.getString("genre_title"))
                        .build();

                bookBuilder.genre(genre);
            }

            long authorId = rs.getLong("author_id");
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
}
