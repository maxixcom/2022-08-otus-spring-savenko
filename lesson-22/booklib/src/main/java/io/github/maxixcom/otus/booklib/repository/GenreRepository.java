package io.github.maxixcom.otus.booklib.repository;

import io.github.maxixcom.otus.booklib.domain.Genre;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    @Query(value = "{}", sort = "{ title : 1 }")
    Flux<Genre> findAllOrderedByTitle();

    Mono<Genre> findByTitle(String title);
}
