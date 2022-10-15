package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    public Optional<Book> findByIdWithAuthorAndGenre(long id) {
        Book book = entityManager.find(
                Book.class,
                id,
                Collections.singletonMap(
                        "javax.persistence.fetchgraph",
                        entityManager.getEntityGraph("book-author-genre-graph")
                )
        );
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> findByIdWithComments(long id) {
        Book book = entityManager.find(
                Book.class,
                id,
                Collections.singletonMap(
                        "javax.persistence.fetchgraph",
                        entityManager.getEntityGraph("book-comments-graph")
                )
        );
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String jpqlQuery = "select distinct b from Book b " +
                "left join fetch b.author " +
                "left join fetch b.genre ";
        return entityManager.createQuery(jpqlQuery, Book.class)
                .getResultList();
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        entityManager.createQuery("delete from Book b where b.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }
}
