package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findBookWithAuthorAndGenreById(long id);

    @EntityGraph(attributePaths = {"bookComments"})
    Optional<Book> findWithCommentsById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Query(value = "select distinct b from Book b " +
            "                left join fetch b.author " +
            "                left join fetch b.genre")
    List<Book> findAllWithAuthorAndGenres();
}
