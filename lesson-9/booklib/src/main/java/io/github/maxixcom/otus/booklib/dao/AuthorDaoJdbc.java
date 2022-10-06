package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Author;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        String sql = "SELECT id, name FROM author WHERE id=:id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        Author author = jdbc.queryForObject(sql, parameterSource, new AuthorRowMapper());
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        String sql = "SELECT id, name FROM author";

        return jdbc.query(sql, new AuthorRowMapper());
    }

    @Override
    public long insert(Author author) {
        String sql = "INSERT INTO author(name) VALUES(:name)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", author.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(sql, parameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
