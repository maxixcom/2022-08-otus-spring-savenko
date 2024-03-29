package io.github.maxixcom.otus.booklib.rest.controller;

import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    List<BookDto> listBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/book/{id}")
    ResponseEntity<BookDto> getBook(@PathVariable long id) {
        BookDto bookDto = bookService.getBookById(id).orElseThrow(BookNotFoundException::new);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping("/api/book")
    ResponseEntity<Object> createBook(@Valid @RequestBody CreateBookDto createBookDto) {
        long id = bookService.createBook(createBookDto);
        return ResponseEntity
                .created(URI.create("/api/book/" + id))
                .build();
    }

    @PutMapping("/api/book/{id}")
    ResponseEntity<BookDto> editBook(@Valid @RequestBody UpdateBookDto updateBookDto, @PathVariable long id) {
        BookDto bookDto = bookService.updateBook(id, updateBookDto);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/api/book/{id}")
    ResponseEntity<Object> deleteBook(@PathVariable long id) {
        bookService.deleteBooks(Set.of(id));
        return ResponseEntity.noContent().build();
    }

}
