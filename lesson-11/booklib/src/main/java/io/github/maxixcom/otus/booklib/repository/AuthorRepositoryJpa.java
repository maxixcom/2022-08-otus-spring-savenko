package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    private final EntityManager entityManager;

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("select a from Author a", Author.class)
                .getResultList();
    }
}
