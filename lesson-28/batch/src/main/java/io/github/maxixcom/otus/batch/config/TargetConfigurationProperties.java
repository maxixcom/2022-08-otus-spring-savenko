package io.github.maxixcom.otus.batch.config;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.BooksCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "target")
@ConstructorBinding
@RequiredArgsConstructor
public class TargetConfigurationProperties implements AuthorsCollectionProvider, GenresCollectionProvider, BooksCollectionProvider {
    private final String authorsCollection;
    private final String genresCollection;
    private final String booksCollection;

    @Override
    public String getAuthorsCollection() {
        return this.authorsCollection;
    }

    @Override
    public String getGenresCollection() {
        return this.genresCollection;
    }

    @Override
    public String getBooksCollection() {
        return this.booksCollection;
    }
}
