package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<Book> getAllBooks();

    Book createBook(CreateBookDto dto);

    Book updateBook(UpdateBookDto dto);

    void deleteBooks(Set<Long> bookIds);

    Optional<Book> getBookById(long id);

    @Data
    class CreateBookDto {
        private final String title;
        private final Long authorId;
        private final Long genreId;
    }

    @Data
    class UpdateBookDto {
        private long bookId;
        private String title;
        private Long authorId;
        private Long genreId;
    }
}
