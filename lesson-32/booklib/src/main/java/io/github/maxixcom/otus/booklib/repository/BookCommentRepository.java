package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}
