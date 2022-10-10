package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJpa implements GenreRepository {
    private final EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("select a from Genre a", Genre.class)
                .getResultList();
    }
}
