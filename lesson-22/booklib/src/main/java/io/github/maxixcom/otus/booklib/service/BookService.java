package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<BookDto> getAllBooks();

    long createBook(CreateBookDto dto);

    BookDto updateBook(long id, UpdateBookDto dto);

    void deleteBooks(Set<Long> bookIds);

    Optional<BookDto> getBookById(long id);
}
