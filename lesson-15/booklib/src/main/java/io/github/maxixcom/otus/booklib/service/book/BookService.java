package io.github.maxixcom.otus.booklib.service.book;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.service.book.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.service.book.dto.UpdateBookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<Book> getAllBooks();

    Book createBook(CreateBookDto dto);

    Book updateBook(UpdateBookDto dto);

    void deleteBooks(Set<String> bookIds);

    Optional<Book> getBookById(String id);
}
