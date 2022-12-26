package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.BooksCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CleanUpTargetService {
    private final MongoTemplate mongoTemplate;
    private final AuthorsCollectionProvider authorsCollectionProvider;
    private final GenresCollectionProvider genresCollectionProvider;
    private final BooksCollectionProvider booksCollectionProvider;

    public void cleanUp() {
        mongoTemplate.dropCollection(this.authorsCollectionProvider.getAuthorsCollection());
        mongoTemplate.dropCollection(this.genresCollectionProvider.getGenresCollection());
        mongoTemplate.dropCollection(this.booksCollectionProvider.getBooksCollection());
    }
}
