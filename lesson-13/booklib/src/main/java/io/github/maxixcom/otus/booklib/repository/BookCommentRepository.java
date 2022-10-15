package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}
