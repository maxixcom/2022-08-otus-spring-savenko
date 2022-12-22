package io.github.maxixcom.otus.batch.service;

import io.github.maxixcom.otus.batch.config.provider.AuthorsCollectionProvider;
import io.github.maxixcom.otus.batch.config.provider.GenresCollectionProvider;
import io.github.maxixcom.otus.batch.domain.Author;
import io.github.maxixcom.otus.batch.domain.Book;
import io.github.maxixcom.otus.batch.domain.Genre;
import io.github.maxixcom.otus.batch.mongo.document.AuthorDocument;
import io.github.maxixcom.otus.batch.mongo.document.BookDocument;
import io.github.maxixcom.otus.batch.mongo.document.GenreDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootTest(classes = BookItemProcessor.class)
class BookItemProcessorTest {
    @MockBean
    private MongoTemplate mongoTemplate;
    @MockBean
    private AuthorsCollectionProvider authorsCollectionProvider;
    @MockBean
    private GenresCollectionProvider genresCollectionProvider;
    @Autowired
    private BookItemProcessor bookItemProcessor;

    @Test
    void process() throws Exception {
        Mockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.eq(AuthorDocument.class), Mockito.any()))
                .thenReturn(new AuthorDocument("authorId", "Author name"));

        Mockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.eq(GenreDocument.class), Mockito.any()))
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