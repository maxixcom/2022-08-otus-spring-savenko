package io.github.maxixcom.otus.booklib.rest.controller;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    Flux<BookDto> listBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/book/{id}")
    Mono<ResponseEntity<BookDto>> getBook(@PathVariable String id) {
        return this.bookService.getBookById(id)
                .map(dto -> ResponseEntity.ok().body(dto))
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/api/book")
    Mono<ResponseEntity<Object>> createBook(@RequestBody Mono<CreateBookDto> createBookDto) {
        return bookService.createBook(createBookDto)
                .map(id ->
                        ResponseEntity
                                .created(URI.create("/api/book/" + id))
                                .build()
                );
    }

    @PutMapping("/api/book/{id}")
    Mono<ResponseEntity<BookDto>> editBook(@RequestBody Mono<UpdateBookDto> updateBookDto, @PathVariable String id) {
        return bookService.updateBook(id, updateBookDto)
                .map(dto -> ResponseEntity.ok().body(dto))
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/book/{id}")
    Mono<ResponseEntity<Object>> deleteBook(@PathVariable String id) {
        return bookService.deleteBooks(Set.of(id))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
