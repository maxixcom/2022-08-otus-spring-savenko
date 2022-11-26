package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface BookService {
    Flux<BookDto> getAllBooks();

    Mono<String> createBook(Mono<CreateBookDto> dto);

    Mono<BookDto> updateBook(String id, Mono<UpdateBookDto> dto);

    Mono<Void> deleteBooks(Set<String> bookIds);

    Mono<BookDto> getBookById(String id);
}
