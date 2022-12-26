package io.github.maxixcom.otus.batch.service.mongo;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorCachingService {
    private final MongoTemplate mongoTemplate;
    private final AuthorsCollectionProvider authorsCollectionProvider;

    @Cacheable(value = "author", key = "#name")
    public AuthorDocument findCaching(String name) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("name").is(name)),
                AuthorDocument.class,
                authorsCollectionProvider.getAuthorsCollection());
    }
}
