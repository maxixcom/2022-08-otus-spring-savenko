package io.github.maxixcom.otus.batch.repository;

import io.github.maxixcom.otus.batch.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
