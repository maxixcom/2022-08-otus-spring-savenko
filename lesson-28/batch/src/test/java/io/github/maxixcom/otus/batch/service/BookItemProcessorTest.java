package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.domain.Author;
import io.github.maxixcom.otus.batch.domain.Book;
import io.github.maxixcom.otus.batch.domain.Genre;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.mongo.document.BookDocument;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import io.github.maxixcom.otus.batch.service.mongo.AuthorCachingService;
import io.github.maxixcom.otus.batch.service.mongo.GenreCachingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = BookItemProcessor.class)
class BookItemProcessorTest {
    @Autowired
    private BookItemProcessor bookItemProcessor;
    @MockBean
    private AuthorCachingService authorCachingService;
    @MockBean
    private GenreCachingService genreCachingService;

    @Test
    void process() throws Exception {
        Mockito.when(authorCachingService.findCaching(Mockito.eq("Author name")))
                .thenReturn(new AuthorDocument("authorId", "Author name"));

        Mockito.when(genreCachingService.findCaching(Mockito.eq("Genre title")))
                .thenReturn(new GenreDocument("genreId", "Genre title"));

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book title");
        book.setAuthor(new Author(2L, "Author name"));
        book.setGenre(new Genre(3L, "Genre title"));

        BookDocument bookDocument = bookItemProcessor.process(book);
        AuthorDocument authorDocument = bookDocument.getAuthor();
        GenreDocument genreDocument = bookDocument.getGenre();

        Assertions.assertThat(bookDocument.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(bookDocument.getAuthor()).isNotNull();
        Assertions.assertThat(bookDocument.getGenre()).isNotNull();

        Assertions.assertThat(authorDocument.getId()).isEqualTo("authorId");
        Assertions.assertThat(authorDocument.getName()).isEqualTo("Author name");

        Assertions.assertThat(genreDocument.getId()).isEqualTo("genreId");
        Assertions.assertThat(genreDocument.getTitle()).isEqualTo("Genre title");

    }

}