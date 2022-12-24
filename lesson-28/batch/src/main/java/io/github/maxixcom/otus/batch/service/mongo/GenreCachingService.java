package io.github.maxixcom.otus.batch.service.mongo;

import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreCachingService {
    private final MongoTemplate mongoTemplate;
    private final GenresCollectionProvider genresCollectionProvider;

    @Cacheable(value = "genre", key = "#title")
    public GenreDocument findCaching(String title) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("title").is(title)),
                GenreDocument.class,
                genresCollectionProvider.getGenresCollection()
        );
    }
}
