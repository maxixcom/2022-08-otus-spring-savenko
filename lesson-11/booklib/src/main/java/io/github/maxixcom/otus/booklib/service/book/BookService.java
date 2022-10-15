package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;
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
}
