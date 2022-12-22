package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import io.github.maxixcom.otus.batch.domain.Book;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.mongo.document.BookDocument;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookItemProcessor implements ItemProcessor<Book, BookDocument> {
    private final AuthorsCollectionProvider authorsCollectionProvider;
    private final GenresCollectionProvider genresCollectionProvider;
    private final MongoTemplate mongoTemplate;

    @Override
    public BookDocument process(Book item) throws Exception {
        Optional<AuthorDocument> authorDtoOptional = Optional.ofNullable(item.getAuthor())
                .map(name -> mongoTemplate.findOne(
                                Query.query(Criteria.where("name").is(item.getAuthor().getName())),
                                AuthorDocument.class,
                                authorsCollectionProvider.getAuthorsCollection()
                        )
                );

        Optional<GenreDocument> genreDtoOptional = Optional.ofNullable(item.getGenre())
                .map(title -> mongoTemplate.findOne(
                                Query.query(Criteria.where("title").is(item.getGenre().getTitle())),
                                GenreDocument.class,
                                genresCollectionProvider.getGenresCollection()
                        )
                );

        return new BookDocument(
                null,
                item.getTitle(),
                authorDtoOptional.orElse(null),
                genreDtoOptional.orElse(null)
        );
    }
}
