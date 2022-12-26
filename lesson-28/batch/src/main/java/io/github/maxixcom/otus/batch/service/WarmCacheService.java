package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarmCacheService {
    private final MongoTemplate mongoTemplate;
    private final AuthorsCollectionProvider authorsCollectionProvider;
    private final GenresCollectionProvider genresCollectionProvider;
    private final CacheManager cacheManager;

    public void warmCache() {
        Cache authorCache = cacheManager.getCache("author");
        mongoTemplate.findAll(AuthorDocument.class, this.authorsCollectionProvider.getAuthorsCollection())
                .forEach(authorDocument -> authorCache.put(authorDocument.getName(), authorDocument));

        Cache genreCache = cacheManager.getCache("genre");
        mongoTemplate.findAll(GenreDocument.class, this.genresCollectionProvider.getGenresCollection())
                .forEach(genreDocument -> genreCache.put(genreDocument.getTitle(), genreDocument));
    }
}
