package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.BookComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookCommentRepositoryImpl implements BookCommentRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        entityManager.createQuery("delete from BookComment c where c.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == 0) {
            entityManager.persist(bookComment);
            return bookComment;
        }
        return entityManager.merge(bookComment);
    }
}
