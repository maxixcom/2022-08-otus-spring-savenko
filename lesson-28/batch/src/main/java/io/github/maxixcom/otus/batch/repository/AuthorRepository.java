package io.github.maxixcom.otus.batch.repository;

import io.github.maxixcom.otus.batch.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
