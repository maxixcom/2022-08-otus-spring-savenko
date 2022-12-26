package io.github.maxixcom.otus.batch.repository;

import io.github.maxixcom.otus.batch.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
