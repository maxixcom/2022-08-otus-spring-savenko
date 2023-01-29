package io.github.maxixcom.otus.booklib.service;

import io.github.maxixcom.otus.booklib.domain.Book;
import io.github.maxixcom.otus.booklib.dto.BookDto;
import io.github.maxixcom.otus.booklib.dto.CreateBookDto;
import io.github.maxixcom.otus.booklib.dto.UpdateBookDto;
import io.github.maxixcom.otus.booklib.exception.BookNotFoundException;
import io.github.maxixcom.otus.booklib.repository.AuthorRepository;
import io.github.maxixcom.otus.booklib.repository.BookRepository;
import io.github.maxixcom.otus.booklib.repository.GenreRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private final static BookDto EmptyBook = new BookDto(-1, null, null, null);

    @CircuitBreaker(name = "getAllBooks", fallbackMethod = "fallbackGetAllBooks")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAllWithAuthorAndGenres()
                .stream()
                .map(BookDto::fromDomainObject)
                .collect(Collectors.toList());
    }

    List<BookDto> fallbackGetAllBooks(Exception exception) {
        log.debug("method getAllBooks is slow ...");
        return Collections.emptyList();
    }

    @CircuitBreaker(name = "createBook", fallbackMethod = "fallbackCreateBook")
    @Transactional
    @Override
    public long createBook(CreateBookDto dto) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(
                Optional.ofNullable(dto.getAuthorId())
                        .flatMap(authorRepository::findById)
                        .orElse(null)
        );
        book.setGenre(
                Optional.ofNullable(dto.getGenreId())
                        .flatMap(genreRepository::findById)
                        .orElse(null)
        );

        bookRepository.save(book);

        return book.getId();
    }

    long fallbackCreateBook(CreateBookDto dto, Exception exception) {
        log.debug("method createBook is slow ...");
        return -1;
    }

    @CircuitBreaker(name = "updateBook", fallbackMethod = "fallbackUpdateBook")
    @Transactional
    @Override
    public BookDto updateBook(long id, UpdateBookDto dto) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(dto.getTitle());
                    book.setAuthor(
                            Optional.ofNullable(dto.getAuthorId())
                                    .flatMap(authorRepository::findById)
                                    .orElse(null)
                    );
                    book.setGenre(
                            Optional.ofNullable(dto.getGenreId())
                                    .flatMap(genreRepository::findById)
                                    .orElse(null)
                    );
                    return book;
                })
                .map(bookRepository::save)
                .map(BookDto::fromDomainObject)
                .orElseThrow(() -> new BookNotFoundException("Book " + id + " not found"));
    }

    BookDto fallbackUpdateBook(long id, UpdateBookDto dto, Exception exception) {
        log.debug("method updateBook is slow ...");
        return EmptyBook;
    }

    @CircuitBreaker(name = "deleteBooks", fallbackMethod = "fallbackDeleteBooks")
    @Transactional
    @Override
    public void deleteBooks(Set<Long> bookIds) {
        bookRepository.deleteAllByIdInBatch(bookIds);
    }

    void fallbackDeleteBooks(Set<Long> bookIds, Exception exception) {
        log.debug("method deleteBooks is slow ...");
    }

    @CircuitBreaker(name = "getBookById", fallbackMethod = "fallbackGetBookById")
    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> getBookById(long id) {
        return bookRepository.findBookWithAuthorAndGenreById(id).map(BookDto::fromDomainObject);
    }

    Optional<BookDto> fallbackGetBookById(long id, Exception exception) {
        log.debug("method fallbackGetBookById is slow ...");
        return Optional.of(EmptyBook);
    }
}
