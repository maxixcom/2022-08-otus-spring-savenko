package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
