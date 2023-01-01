package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    List<BookDto> getAllBooks();

    Book createBook(CreateBookDto dto);

    Book updateBook(UpdateBookDto dto);

    void deleteBooks(Set<Long> bookIds);

    Optional<BookDto> getBookById(long id);
    Optional<UpdateBookDto> getBookByIdForUpdate(long id);
}
