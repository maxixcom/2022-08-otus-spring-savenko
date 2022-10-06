package io.github.maxixcom.otus.booklib.dao;

import io.github.maxixcom.otus.booklib.domain.Genre;
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
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Genre.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .build();
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        String sql = "SELECT id, title FROM genre WHERE id=:id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        Genre genre = jdbc.queryForObject(sql, parameterSource, new GenreRowMapper());
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT id, title FROM genre";

        return jdbc.query(sql, new GenreRowMapper());
    }

    @Override
    public long insert(Genre genre) {
        String sql = "INSERT INTO genre(title) VALUES(:title)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", genre.getTitle());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(sql, parameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
