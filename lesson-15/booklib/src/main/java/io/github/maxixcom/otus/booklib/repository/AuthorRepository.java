package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, Long> {
}
