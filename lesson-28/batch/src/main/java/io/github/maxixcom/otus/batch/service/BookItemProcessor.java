package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.domain.Author;
import io.github.maxixcom.otus.batch.domain.Book;
import io.github.maxixcom.otus.batch.domain.Genre;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.mongo.document.BookDocument;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import io.github.maxixcom.otus.batch.service.mongo.AuthorCachingService;
import io.github.maxixcom.otus.batch.service.mongo.GenreCachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookItemProcessor implements ItemProcessor<Book, BookDocument> {
    private final AuthorCachingService authorCachingService;
    private final GenreCachingService genreCachingService;

    @Override
    public BookDocument process(Book item) throws Exception {
        Optional<AuthorDocument> authorDtoOptional = Optional.ofNullable(item.getAuthor())
                .map(Author::getName)
                .map(authorCachingService::findCaching);

        Optional<GenreDocument> genreDtoOptional = Optional.ofNullable(item.getGenre())
                .map(Genre::getTitle)
                .map(genreCachingService::findCaching);

        return new BookDocument(
                null,
                item.getTitle(),
                authorDtoOptional.orElse(null),
                genreDtoOptional.orElse(null)
        );
    }
}
